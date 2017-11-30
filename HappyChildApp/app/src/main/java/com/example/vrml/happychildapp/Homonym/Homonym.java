package com.example.vrml.happychildapp.Homonym;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vrml.happychildapp.Jennifer_Code.FireBaseDataBaseTool;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.StarGrading.StarGrading;
import com.example.vrml.happychildapp.TimeGame.TimeGame;
import com.example.vrml.happychildapp.TurnCardGame.Turn_Card_Game;
import com.example.vrml.happychildapp.menu_choose;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class Homonym extends AppCompatActivity {
    TextView questiontext;
    ImageView questionImage;
    Button option1, option2, option3;
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
    AlertDialog isExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homonym);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        DialogSet();
        questiontext = (TextView) findViewById(R.id.question);
        questionImage = (ImageView) findViewById(R.id.questionImage);
        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
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
        questiontext.setTextSize(metrics.widthPixels / 40);
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
        questiontext.setText(title.get(index));
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
                SharedPreferences sharedPreferences = getSharedPreferences("User", MODE_PRIVATE);
                String User = sharedPreferences.getString("Name", "");
                timeup = System.currentTimeMillis();
                totaltime = (timeup - startTime) / 1000;
                ShowMessage("答對了" + count + "題\n" + "共花了" + totaltime + "秒");
                int star = StarGrading.getStar(bundle.getString("Unit"), title.size(), count);
                FireBaseDataBaseTool.SendStudyRecord(bundle.getString("Unit"), User, "答對了" + count + "題," + "共花了" + totaltime + "秒,Star:" + star);
            }

        }
    };

    private void ShowMessage(String str) {
        new AlertDialog.Builder(this).setMessage(str)
                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Homonym.this, menu_choose.class));
                    }
                }).setCancelable(false).show();

    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    startActivity(new Intent(Homonym.this, menu_choose.class));
                    Homonym.this.finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:

                    break;
                default:
                    break;
            }
        }
    };

    private void DialogSet() {
        isExit = new AlertDialog.Builder(this)
                .setTitle("離開")
                .setMessage("確定要退出嗎?")
                .setPositiveButton("Yes", listener)
                .setNegativeButton("No", listener)
                .setCancelable(false)
                .create();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isExit.show();
        }
        return true;
    }
}
