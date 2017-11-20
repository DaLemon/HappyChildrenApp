package com.example.vrml.happychildapp.Jennifer_Code;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.vrml.happychildapp.R;

/**
 * Created by Nora on 2017/11/20.
 */

public class DownloadView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_layout);

        Bundle bundle = getIntent().getExtras();

        if (bundle.getString("TypeName","").equals(".ppt")){

            startActivity(new Intent(DownloadView.this,pptShow.class).putExtras(bundle));
        }else {
            //VideoView
        }
    }
}
