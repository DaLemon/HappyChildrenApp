package com.example.vrml.happychildapp.Jennifer_Code;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.vrml.happychildapp.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Nora on 2017/11/3.
 */

public class SignOut {
    public SignOut(){
        FirebaseAuth.getInstance().signOut();
    }

}
