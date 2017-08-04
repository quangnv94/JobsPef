package duan2.jobspef.luyquangdat.com.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.libre.mylibs.MyUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duan2.jobspef.luyquangdat.com.myapplication.common.Constants;
import duan2.jobspef.luyquangdat.com.myapplication.entity.LoginResponse;
import duan2.jobspef.luyquangdat.com.myapplication.entity.SimpleResponse;
import duan2.jobspef.luyquangdat.com.myapplication.service.ConnectServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private Button btnSignUp;
    private Button btnFacebook;

    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtName;
    private EditText edtRePassword;

    private ImageView imgBack;
    private String facebookId;

    private CallbackManager callbackManager;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_register);
        intent = getIntent();
        MyUtils.hideKeyboard(RegisterActivity.this);
        init();
    }

    public void init() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnFacebook = (Button) findViewById(R.id.btnFacebook);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtName = (EditText) findViewById(R.id.edtName);
        edtRePassword = (EditText) findViewById(R.id.edtRePassword);

        imgBack = (ImageView) findViewById(R.id.imgBack);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);

        if (intent != null) {
            edtEmail.setText(intent.getStringExtra(Constants.EMAIL));
            edtName.setText(intent.getStringExtra(Constants.NAME));
            facebookId =  intent.getStringExtra(Constants.FACEBOOK_ID);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLogin:
                onBackPressed();
                break;
            case R.id.btnSignUp:
                registerAction();
                break;
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.btnFacebook:
                loginWithFaceBook();
                break;
        }
    }

    private void loginWithFaceBook() {
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile,email"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        try {
                                            facebookId = object.getString("id");
                                            checkFacebookIsReady(facebookId, object.getString("email"),object.getString("name"));
                                        } catch (JSONException e) {
                                            e.toString();
                                        }
                                    }

                                });
                        //get avatar http:// graph.facebook.com/{facebook-Id}/picture?width=160&height=160
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
                        showDialogConfirm(R.drawable.ic_back, R.style.DialogAnimationBottom,
                                getString(R.string.error_unkonw), getString(R.string.error));
                    }
                });
    }

    public void checkFacebookIsReady(final String facebookId, final String emailface, final String nameFace) {
        AppUtils.showProgressDialog(RegisterActivity.this, getString(R.string.loading));
        ConnectServer.getResponseAPI().login(emailface, nameFace, facebookId).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                MyUtils.hideKeyboard(RegisterActivity.this);
                AppUtils.hideProgressDialog(RegisterActivity.this);
                if (!response.isSuccessful()) {
                    if (facebookId != null) {
                        edtName.setText(nameFace);
                        edtEmail.setText(emailface);
                    }
                } else {
                    MyUtils.insertStringData(getApplicationContext(), Constants.NAME, response.body().getProfile().getName());
                    MyUtils.insertStringData(getApplicationContext(), Constants.TOKEN, response.body().getUser().getToken());
                    MyUtils.insertStringData(getApplicationContext(), Constants.IMAGE_ID, response.body().getProfile().getAvatar_id());
                    MyUtils.insertStringData(getApplicationContext(), Constants.EMAIL_CONTACT, response.body().getProfile().getContact_emal());
                    MyUtils.insertStringData(getApplicationContext(), Constants.PROFILE_ID, response.body().getUser().getProfile_id());
                    MyUtils.insertStringData(getApplicationContext(), Constants.USER_ID, response.body().getUser().getUser_id());
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                MyUtils.hideKeyboard(RegisterActivity.this);
                AppUtils.hideProgressDialog(RegisterActivity.this);
                showDialogConfirm(R.drawable.warning, R.style.DialogAnimationBottom,
                        getString(R.string.error_account), getString(R.string.error));
            }
        });
    }

    public void valiDateData(String name, String email, String password, String repassword) {
        if (name.length() == 0 || name.length() < 8) {
            edtName.setError(getResources().getString(R.string.name_too_short));
            edtName.requestFocus();
        } else if (email.length() == 0) {
            edtEmail.setError(getResources().getString(R.string.email_empty));
            edtEmail.requestFocus();
        } else if (!emailValidator(email)) {
            edtEmail.setError(getResources().getString(R.string.email_error));
            edtEmail.requestFocus();
        } else if (password.length() < 6) {
            edtPassword.setError(getResources().getString(R.string.password_empty));
            edtPassword.requestFocus();
        } else if (!repassword.equals(password) || repassword.length() < 6) {
            edtRePassword.setError(getResources().getString(R.string.password_erro));
            edtRePassword.requestFocus();
        } else {
            registerRequest(name, email, password, facebookId);
        }
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void registerAction() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String repassword = edtRePassword.getText().toString().trim();
        valiDateData(name, email, password, repassword);
    }

    public void registerRequest(String name, String email, String password, String facebookId) {
        MyUtils.hideKeyboard(RegisterActivity.this);
        AppUtils.showProgressDialog(RegisterActivity.this, getString(R.string.loading));
        ConnectServer.getResponseAPI().register(name, email, password, facebookId).enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                AppUtils.hideProgressDialog(RegisterActivity.this);
                if (response.body().getStatus() == 404) {
                    showDialogConfirm(R.drawable.warning, R.style.DialogAnimationBottom,
                            response.body().getMessage(), getString(R.string.error));
                } else {
                    showDialogConfirm(R.drawable.success, R.style.DialogAnimationBottom,
                            getString(R.string.register_success), getString(R.string.success));
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                AppUtils.hideProgressDialog(RegisterActivity.this);
                showDialogConfirm(R.drawable.ic_back, R.style.DialogAnimationBottom,
                        getString(R.string.error_account), getString(R.string.error));
            }
        });
    }

    public void showDialogConfirm(int icon, int animationSource, String message, String title) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);

        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setIcon(icon);

        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra(Constants.EMAIL, edtEmail.getText().toString().trim());
                        startActivity(intent);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = animationSource;
        alertDialog.show();


    }

    @Override
    public void onActivityResult(int requestCode, int resultdata, Intent data) {
        callbackManager.onActivityResult(requestCode, resultdata, data);
    }
}
