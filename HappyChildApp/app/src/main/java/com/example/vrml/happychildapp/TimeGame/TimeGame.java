package com.example.vrml.happychildapp.TimeGame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.vrml.happychildapp.Homonym.Homonym;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.menu_choose;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Nora on 2017/11/3.
 */

public class TimeGame extends AppCompatActivity {
    TextView question;
    Button option1,option2,option3;
    DisplayMetrics metrics = new DisplayMetrics();
    List<String> title = new ArrayList<String>();
    List<String[]> content = new ArrayList<String[]>();
    List<String> answer = new ArrayList<String>();

    String temp2;
    int count = 0;//答對題數
    Integer[] array = new Integer[]{0, 1, 2};
    Bundle bundle;
    private int index = 0;
    private long startTime, timeup, totaltime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_game);
        question = (TextView) findViewById(R.id.timequestion);
        option1 = (Button) findViewById(R.id.timeoption1);
        option2 = (Button) findViewById(R.id.timeoption2);
        option3 = (Button) findViewById(R.id.timeoption3);
        option1.setOnClickListener(click);
        option2.setOnClickListener(click);
        option3.setOnClickListener(click);
        size();
        bundle = this.getIntent().getExtras();
        getdataFromFirebase();

        startTime = System.currentTimeMillis();
    }

    private void size() {
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        question.setTextSize(metrics.widthPixels / 40);
        option1.setTextSize(metrics.widthPixels / 40);
        option2.setTextSize(metrics.widthPixels / 40);
        option3.setTextSize(metrics.widthPixels / 40);
    }
    private void getdataFromFirebase() {

        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Teach");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Subject = bundle.getString("Subject");
                String Mode = bundle.getString("Mode");
                String Unit = bundle.getString("Unit");
                String Lesson = bundle.getString("Lesson");
                dataSnapshot = dataSnapshot.child(Subject).child(Mode).child(Unit).child(Lesson);
                for (DataSnapshot temp : dataSnapshot.getChildren()) {

                    temp2 = (String) temp.getValue();
                    title.add(temp2.split(",")[0]);
                    content.add(new String[]{temp2.split(",")[1] + "", temp2.split(",")[2] + "", temp2.split(",")[3] + ""});
                    answer.add(temp2.split(",")[1]);
                }

                setData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setData() {

        Collections.shuffle(Arrays.asList(array));
        question.setText(title.get(index));
        option1.setText(content.get(index)[array[0]]);
        option2.setText(content.get(index)[array[1]]);
        option3.setText(content.get(index++)[array[2]]);

    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Button button = (Button) view;
            String string = button.getText().toString();

            if (string.equals(answer.get(index - 1))) {
                count++;
            }
            if (index < title.size()) {
                setData();
            } else {//差上傳資料
                timeup = System.currentTimeMillis();
                totaltime = (timeup - startTime) / 1000;
                ShowMessage("答對了" + count + "題\n" + "共花了" + totaltime + "秒");
            }

        }
    };
    private void ShowMessage(String str) {
        new AlertDialog.Builder(this).setMessage(str)
                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(TimeGame.this, menu_choose.class));
                    }
                }).setCancelable(false).show();

    }
}
