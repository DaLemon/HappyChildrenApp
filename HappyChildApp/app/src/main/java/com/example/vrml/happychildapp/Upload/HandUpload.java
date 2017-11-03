package com.example.vrml.happychildapp.Upload;

import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vrml.happychildapp.Jennifer_Code.FireBaseDataBaseTool;
import com.example.vrml.happychildapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nora on 2017/11/4.
 */

public class HandUpload extends AppCompatActivity {

    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handupload);
        button = (Button) findViewById(R.id.summit);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText1;
                String result = "";
                for (int i = 0; i < 16; i++) {
                    String btn = "editText"+(i+1);
                    int resID = getResources().getIdentifier(btn, "id", getPackageName());
                    editText1 = (EditText)findViewById(resID);
                    result+= editText1.getText();
                }Log.e("DEBUG",result+"");
                List<String> path= new ArrayList<String>();
                path.add("Teach");
                path.add("Chinese");
                path.add("Exam");
                path.add("Hand");
                path.add("Lesson3");
                path.add("content");
                FireBaseDataBaseTool.SendText(path,result);
            }
        });
    }
}
