package com.example.vrml.happychildapp.Upload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.vrml.happychildapp.menu_choose;
import com.example.vrml.happychildapp.Jennifer_Code.FireBaseDataBaseTool;
import com.example.vrml.happychildapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nora on 2017/11/4.
 */

public class HomonymUpload extends AppCompatActivity {
    Bundle bundle;
    EditText homonymTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homonymupload);
        Button submit = (Button)findViewById(R.id.homonymsubmit);
        homonymTitle = (EditText)findViewById(R.id.homonymTitle);
        bundle = HomonymUpload.this.getIntent().getExtras();
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText homonymeditText;
                String result = "";
                for (int i = 0; i < 4; i++) {
                    String btn = "homonymeditText"+(i+1);
                    int resID = getResources().getIdentifier(btn, "id", getPackageName());
                    homonymeditText = (EditText)findViewById(resID);
                    result+= homonymeditText.getText()+(i<3?",":"");
                }
                Log.e("DEBUG",""+result);
                List<String> path= new ArrayList<String>();
                path.add("Teach");
                path.add(bundle.getString("Subject"));
                path.add(bundle.getString("Mode"));
                path.add(bundle.getString("Unit"));
                path.add(homonymTitle.getText().toString());//Lesson3 Lesson4??
                path.add("content");
                FireBaseDataBaseTool.SendText(path,result);
                Intent intent = new Intent(HomonymUpload.this,menu_choose.class);
                startActivity(intent);            }
        });
    }


}
