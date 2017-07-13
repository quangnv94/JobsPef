package duan2.jobspef.luyquangdat.com.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private Button btnSignUp;
    private Button btnFacebook;
    private EditText edtEmail;
    private EditText edtPassword;
    LoginButton btnfacebook;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnfacebook = (LoginButton) findViewById(R.id.btnFacebook);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        btnfacebook.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                actionLogin();
                break;
            case R.id.btnSignUp:
                Log.d("daclick", "dasd");
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.btnFacebook:
                actionLoginFacebook();
                break;
        }
    }

    public void actionLogin() {
        Intent intent = new Intent(this
                , MainActivity.class);
        startActivity(intent);
    }

    public void actionLoginFacebook() {
        callbackManager = CallbackManager.Factory.create();
        btnfacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "login succes", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    protected void onActivityResult(int requestCode, int resultdata, Intent data) {
        callbackManager.onActivityResult(requestCode, resultdata, data);

    }


    public boolean validateFourm() {

        return true;
    }
}
