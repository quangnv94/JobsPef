package duan2.jobspef.luyquangdat.com.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnLogin;
    private Button btnSignUp;
    private Button btnFacebook;

    private EditText edtEmail;
    private EditText edtPassword;

    private ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnFacebook = (Button) findViewById(R.id.btnFacebook);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        imgBack = (ImageView) findViewById(R.id.imgBack);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        btnFacebook.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnLogin:
                onBackPressed();
                break;
            case R.id.btnSignUp:
                break;
            case R.id.imgBack:
                onBackPressed();
                break;
            case R.id.btnFacebook:
                break;
        }
    }
}
