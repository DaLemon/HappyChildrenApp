package com.example.vrml.happychildapp.Upload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vrml.happychildapp.HYC_Code.MultiplicationTable.MultiplicationTable;
import com.example.vrml.happychildapp.Jennifer_Code.FireBaseDataBaseTool;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.menu_choose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nora on 2017/11/7.
 */

public class MultiplicationTableUpload extends AppCompatActivity {
    Bundle bundle;
    Button button;
    EditText handTitle;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.handupload);
        for (int i = 0; i < 16; i++) {
            String btn = "editText"+(i+1);
            int resID = getResources().getIdentifier(btn, "id", getPackageName());
            EditText editText1 = (EditText)findViewById(resID);
            int maxLengthofEditText = 10;
            editText1.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLengthofEditText)});
        }
        button = (Button) findViewById(R.id.summit);
        handTitle = (EditText)findViewById(R.id.handTitle);
        bundle = MultiplicationTableUpload.this.getIntent().getExtras();
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
                }
                Log.e("DEBUG",result+"");
                List<String> path= new ArrayList<String>();
                path.add("Teach");
                path.add(bundle.getString("Subject"));
                path.add(bundle.getString("Mode"));
                path.add(bundle.getString("Unit"));
                path.add(handTitle.getText().toString());
                path.add("content");
                FireBaseDataBaseTool.SendText(path,result);
                Intent intent = new Intent(MultiplicationTableUpload.this,menu_choose.class);
                startActivity(intent);
            }
        });
    }
}
