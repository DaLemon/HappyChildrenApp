package com.example.vrml.happychildapp.Jennifer_Code;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vrml.happychildapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private TextView tvUserEmail;
    private Button buttonLogout;
    private DatabaseReference databaseReference;
    private EditText etUserName, etUserposition;
    private Button buttonSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        //回到登入介面
        if (firebaseAuth.getCurrentUser() == null) {

            startActivity(new Intent(ProfileActivity.this, loginActivity.class));
            ProfileActivity.this.finish();
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        //一般登出畫面
        tvUserEmail = (TextView) findViewById(R.id.tvUserEmail);
        buttonLogout = (Button) findViewById(R.id.buttonlogout);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etUserposition = (EditText) findViewById(R.id.etUserposition);
        buttonSave = (Button) findViewById(R.id.buttonsave);

        //顯示使用者信箱
        tvUserEmail.setText("歡迎" + user.getEmail() + "加入!");



      buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     saveUserInformation();
            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                startActivity(new Intent(ProfileActivity.this, loginActivity.class));
                ProfileActivity.this.finish();
            }
        });


    }

    //資料儲存
    /*private void saveUserInformation() {
        String name = etUserName.getText().toString().trim();
        String position = etUserposition.getText().toString().trim();

        UserInformation userInformation = new UserInformation(name,position);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInformation);

        Toast.makeText(this, "儲存資料中...", Toast.LENGTH_LONG).show();
    }*/
}
