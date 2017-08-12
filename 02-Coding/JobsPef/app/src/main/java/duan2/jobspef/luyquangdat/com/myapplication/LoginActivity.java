package duan2.jobspef.luyquangdat.com.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.libre.mylibs.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.LoginResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private Button btnSignUp;
    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnfacebook;
    CallbackManager callbackManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        checkLogined();
        init();
    }

    public void init() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnfacebook = (Button) findViewById(R.id.btnFacebook);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnfacebook.setOnClickListener(this);

        intent = getIntent();
        if (intent != null) {
            edtEmail.setText(intent.getStringExtra(Constants.EMAIL));
        }
        MyUtils.hideKeyboard(LoginActivity.this);
    }

    public void checkLogined() {
        if (MyUtils.getStringData(LoginActivity.this, Constants.TOKEN) == null ||
                (MyUtils.getStringData(LoginActivity.this, Constants.TOKEN)).equals("")) {
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                actionLogin();
                break;
            case R.id.btnSignUp:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btnFacebook:
                loginWithFaceBook();
                break;
        }
    }

    public void actionLogin() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        if (email.length() == 0) {
            edtEmail.setError(getResources().getString(R.string.email_empty));
        } else if (!emailValidator(email)) {
            edtEmail.setError(getResources().getString(R.string.email_error));
        } else if (password.length() == 0) {
            edtPassword.setError(getResources().getString(R.string.password_empty));
        } else {
            actionLoginFinal(email, password);
        }
    }

    public void actionLoginFinal(String email, String pass) {
        AppUtils.showProgressDialog(LoginActivity.this, getString(R.string.loading));
        ConnectServer.getResponseAPI().login(email, pass, "").enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                MyUtils.hideKeyboard(LoginActivity.this);
                AppUtils.hideProgressDialog(LoginActivity.this);
                if (!response.isSuccessful()) {
                    showDialogConfirm(R.drawable.warning, R.style.DialogAnimationBottom,
                            getString(R.string.error_account), getString(R.string.error));
                } else {
                    MyUtils.insertStringData(getApplicationContext(), Constants.TOKEN, response.body().getUser().getToken());
                    MyUtils.insertStringData(getApplicationContext(), Constants.NAME, response.body().getProfile().getName());
                    MyUtils.insertStringData(getApplicationContext(), Constants.IMAGE_ID, response.body().getProfile().getAvatar_id());
                    MyUtils.insertStringData(getApplicationContext(), Constants.EMAIL_CONTACT, response.body().getProfile().getContact_emal());
                    MyUtils.insertStringData(getApplicationContext(), Constants.PROFILE_ID, response.body().getUser().getProfile_id());
                    MyUtils.insertStringData(getApplicationContext(), Constants.USER_ID, response.body().getUser().getUser_id());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                MyUtils.hideKeyboard(LoginActivity.this);
                AppUtils.hideProgressDialog(LoginActivity.this);
                showDialogConfirm(R.drawable.warning, R.style.DialogAnimationBottom,
                        getString(R.string.error_account), getString(R.string.error));
            }
        });
    }

    private void loginWithFaceBook() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile, email"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            checkFacebookIsReady("", "", object.getString("id"), object.getString("email"), object.getString("name"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            try {
                                                checkFacebookIsReady("", "", object.getString("id"), "", object.getString("name"));
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                        }

                                    }

                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,first_name,last_name,gender,location{location}");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("chuyengidasdas1", error.toString());
                        showDialogConfirm(R.drawable.ic_back, R.style.DialogAnimationBottom,
                                getString(R.string.error_unkonw), getString(R.string.error));
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultdata, Intent data) {
        callbackManager.onActivityResult(requestCode, resultdata, data);
    }

    public void checkFacebookIsReady(String email, String pass, final String facebookId, final String emailface, final String nameFace) {
        AppUtils.showProgressDialog(LoginActivity.this, getString(R.string.loading));
        ConnectServer.getResponseAPI().login(email, pass, facebookId).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                MyUtils.hideKeyboard(LoginActivity.this);
                AppUtils.hideProgressDialog(LoginActivity.this);
                if (!response.isSuccessful()) {
                    if (facebookId != null) {
                        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                        intent.putExtra(Constants.EMAIL, emailface);
                        intent.putExtra(Constants.FACEBOOK_ID, facebookId);
                        intent.putExtra(Constants.NAME, nameFace);
                        startActivity(intent);
                    }
                } else {
                    MyUtils.insertStringData(getApplicationContext(), Constants.TOKEN, response.body().getUser().getToken());
                    MyUtils.insertStringData(getApplicationContext(), Constants.NAME, response.body().getProfile().getName());
                    MyUtils.insertStringData(getApplicationContext(), Constants.IMAGE_ID, response.body().getProfile().getAvatar_id());
                    MyUtils.insertStringData(getApplicationContext(), Constants.EMAIL_CONTACT, response.body().getProfile().getContact_emal());
                    MyUtils.insertStringData(getApplicationContext(), Constants.PROFILE_ID, response.body().getUser().getProfile_id());
                    MyUtils.insertStringData(getApplicationContext(), Constants.USER_ID, response.body().getUser().getUser_id());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("chuyengidasdas11", t.toString());
                MyUtils.hideKeyboard(LoginActivity.this);
                AppUtils.hideProgressDialog(LoginActivity.this);
                showDialogConfirm(R.drawable.warning, R.style.DialogAnimationBottom,
                        getString(R.string.error_account), getString(R.string.error));
            }
        });
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void showDialogConfirm(int icon, int animationSource, String message, String title) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(icon);

        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = animationSource;
        alertDialog.show();


    }
}
