package com.example.vrml.happychildapp.Upload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vrml.happychildapp.Jennifer_Code.FireBaseDataBaseTool;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.menu_choose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nora on 2017/11/6.
 */

public class AddSubUpload extends AppCompatActivity {
    Bundle bundle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsubupload);
        bundle = AddSubUpload.this.getIntent().getExtras();
        Button submit = (Button)findViewById(R.id.addsubsubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText n1 = (EditText)findViewById(R.id.addsubNum1);
                EditText n2 = (EditText)findViewById(R.id.addsubNum2);
                EditText ans = (EditText)findViewById(R.id.addsubAns);
                EditText title = (EditText)findViewById(R.id.addsubLesson);
                String result = "";
                result = n1.getText()+",";
                result+= n2.getText()+",";
                result+= ans.getText();
                Log.e("DEBUG",""+result);
                List<String> path= new ArrayList<String>();
                path.add("Teach");
                path.add(bundle.getString("Subject"));
                path.add(bundle.getString("Mode"));
                path.add(bundle.getString("Unit"));
                path.add(title.getText().toString());
                path.add("content1");//要改
                FireBaseDataBaseTool.SendText(path,result);
                Intent intent = new Intent(AddSubUpload.this,menu_choose.class);
                startActivity(intent);
            }
        });
    }
}
