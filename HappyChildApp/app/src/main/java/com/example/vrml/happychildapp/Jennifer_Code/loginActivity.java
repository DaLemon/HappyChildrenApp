package com.example.vrml.happychildapp.Jennifer_Code;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vrml.happychildapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {
    private Button buttonsignin;
    private EditText etemail;
    private EditText etpassword;
    private TextView signup;

    private AlertDialog islogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null ) {
            //start profile activity here
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            loginActivity.this.finish();

        }
        progressDialog = new ProgressDialog(this);
        buttonsignin = (Button) findViewById(R.id.buttonsignin);
        etemail = (EditText) findViewById(R.id.etemail);
        etpassword = (EditText) findViewById(R.id.etpassword);
        signup = (TextView) findViewById(R.id.login);

        buttonsignin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, SignInActivity.class));
                loginActivity.this.finish();
            }
        });
    }

    private void userLogin() {
        String email = etemail.getText().toString().trim();
        String password = etpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "請輸入信箱", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "請輸入密碼", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog = ProgressDialog.show(loginActivity.this,"","登入中...",false,false);
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    loginActivity.this.finish();
                    //else 13:15
                } else {
                    Toast.makeText(loginActivity.this, "登入失敗!請再試一次!!", Toast.LENGTH_SHORT).show();
                }
            }

        });

  /*  @Override
    public void onClick(View v) {

    }*/
    }

}