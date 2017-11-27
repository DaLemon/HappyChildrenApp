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
        homonymTitle = (EditText)findViewById(R.id.Title_1);
        bundle = HomonymUpload.this.getIntent().getExtras();
        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> path= new ArrayList<String>();
                path.add("Teach");
                path.add(bundle.getString("Subject"));
                path.add(bundle.getString("Mode"));
                path.add(bundle.getString("Unit"));
                path.add(homonymTitle.getText().toString());
                for (int i = 0; i < 4; i++) {
                    String Text1 = "Text1_"+(i+1);
                    String Text2 = "Text2_"+(i+1);
                    String Text3 = "Text3_"+(i+1);
                    String Text4 = "Text4_"+(i+1);

                    String result = "";
                    path.add("content"+(i+1));
                    int res1 = getResources().getIdentifier(Text1 ,"id",getPackageName());
                    int res2 = getResources().getIdentifier(Text2 ,"id",getPackageName());
                    int res3 = getResources().getIdentifier(Text3 ,"id",getPackageName());
                    int res4 = getResources().getIdentifier(Text4 ,"id",getPackageName());
                    result+= ((EditText)findViewById(res1)).getText().toString()+",";
                    result+= ((EditText)findViewById(res2)).getText().toString()+",";
                    result+= ((EditText)findViewById(res3)).getText().toString()+",";
                    result+= ((EditText)findViewById(res4)).getText().toString();
                    FireBaseDataBaseTool.SendText(path,result);
                    path.remove("content"+(i+1));
                }

                Intent intent = new Intent(HomonymUpload.this,menu_choose.class);
                startActivity(intent);
            }
        });
    }


}
