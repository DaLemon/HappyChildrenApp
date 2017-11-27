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
        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText title = (EditText)findViewById(R.id.addsubLesson);
                List<String> path= new ArrayList<String>();
                path.add("Teach");
                path.add(bundle.getString("Subject"));
                path.add(bundle.getString("Mode"));
                path.add(bundle.getString("Unit"));
                path.add(title.getText().toString());
                for (int i=0;i<5;i++){
                    String n1 = "Num1_"+(i+1);
                    String n2 = "Num2_"+(i+1);
                    String ans = "Ans_"+(i+1);
                    String result = "";
                    path.add("content"+(i+1));
                    int res1 = getResources().getIdentifier(n1 ,"id",getPackageName());
                    int res2 = getResources().getIdentifier(n2 ,"id",getPackageName());
                    int res3 = getResources().getIdentifier(ans ,"id",getPackageName());
                    result+=((EditText)findViewById(res1)).getText().toString()+",";
                    result+=((EditText)findViewById(res2)).getText().toString()+",";
                    result+=((EditText)findViewById(res3)).getText().toString();
                    FireBaseDataBaseTool.SendText(path,result);
                    path.remove("content"+(i+1));
                }
                Intent intent = new Intent(AddSubUpload.this,menu_choose.class);
                startActivity(intent);
            }
        });
    }
}
