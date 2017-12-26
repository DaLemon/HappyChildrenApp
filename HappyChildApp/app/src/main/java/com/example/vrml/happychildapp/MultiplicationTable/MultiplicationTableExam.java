package com.example.vrml.happychildapp.MultiplicationTable;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;

import com.example.vrml.happychildapp.Choose_Mode;
import com.example.vrml.happychildapp.Jennifer_Code.FireBaseDataBaseTool;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.StarGrading.StarGrading;
import com.example.vrml.happychildapp.TurnCardGame.Turn_Card_Data;
import com.example.vrml.happychildapp.TurnCardGame.Turn_Card_Game;
import com.example.vrml.happychildapp.menu_choose;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Nora on 2017/11/7.
 */

public class MultiplicationTableExam extends AppCompatActivity {

    final Button[] buttons = new Button[16];
    int[] array = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8};
    String[] str_array;
    String text1 = "";
    Button temp = null;
    AlertDialog isExit;
    int count = 0;
    int size;
    RatingBar mRatingBar;
    private static MediaPlayer music;
    DisplayMetrics metrics = new DisplayMetrics();
    private long startTime, timeup, totaltime;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trun_card_game_start);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        getdataFromFirebase();
        StartSet();
        DialogSet();
        startTime = System.currentTimeMillis();

    }
    private void getdataFromFirebase() {

        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Teach");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bundle = MultiplicationTableExam.this.getIntent().getExtras();
                String Subject = bundle.getString("Subject");
                String Mode = bundle.getString("Mode");
                String Unit = bundle.getString("Unit");
                String Lesson = bundle.getString("Lesson");
                dataSnapshot = dataSnapshot.child(Subject).child(Mode).child(Unit).child(Lesson);
                String temp2 = null;
                str_array = new String[16];
                for (DataSnapshot temp : dataSnapshot.getChildren()) {
                    temp2 = (String) temp.getValue();
                }
                String[] temp = temp2.split(",");
                size=temp.length/2;
                for (int i=0 ; i<str_array.length;i++) {
                    str_array[i]  ="";
                }
                for (int i=0;i<temp.length;i++) {
                    str_array[i] = temp[i];
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    //恢復的時候把音樂開起來
    protected void onResume() {
        super.onResume();
        mHandler = new Handler();

        if (!music.isPlaying()) {
            try {

                music.start();
            } catch (Exception e) {
                Log.e("Debug", "TurnCardGame line 51");
                Log.e("Debug", e.toString());
            }
        }

    }

    //Star Button  music start + onClickListener
    private void StartSet() {
        Button star = (Button) findViewById(R.id.start);
        music = MediaPlayer.create(MultiplicationTableExam.this, R.raw.background);
        music.setLooping(true);
        try {
            music.start();
        } catch (Exception e) {
            Log.e("Debug", "TurnCardGame line 51");
            Log.e("Debug", e.toString());
        }
        music.start();
        star.setOnClickListener(staronClickListener);
    }

    //Star的OnClickListener
    private View.OnClickListener staronClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            setContentView(R.layout.turn_card_game);
            Button back = (Button) findViewById(R.id.back);
            mRatingBar = (RatingBar) findViewById(R.id.ratingBar1);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowExit();
                }
            });
            count = 0;
            Rand();
            StarSet();
            ButtonSet();
        }
    };

    //Button 宣告ID+TextSize設定
    private void ButtonSet() {
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Log.e("DEBUG","metrics : "+metrics.toString());
        for (int i = 0; i < 16; i++) {
            String btn = "button" + (i + 1);
            int resID = getResources().getIdentifier(btn, "id", getPackageName());
            buttons[i] = ((Button) findViewById(resID));
            if(TextUtils.isEmpty(str_array[i]))
                buttons[i].setVisibility(View.INVISIBLE);
            else{
                buttons[i].setText(str_array[i]);
                //隨著螢幕寬去縮放文字尺寸
                if(metrics.widthPixels > 2000) {
                    buttons[i].setTextSize(metrics.widthPixels / 50);
                    if (buttons[i].getText().length() > 9) {
                        buttons[i].setTextSize(metrics.widthPixels / 80);
                    }
                }else {
                    buttons[i].setTextSize(metrics.widthPixels / 50);
                    if (buttons[i].getText().length() > 9) {
                        buttons[i].setTextSize(metrics.widthPixels / 120);
                    }
                }
                buttons[i].setTextColor(Color.BLUE);
                buttons[i].setOnClickListener(onClickListener);
//                buttons[i].setBackgroundResource(R.drawable.tt);
            }
        }
    }

    //星星初始設定
    private void StarSet() {
        mRatingBar.setMax(100);
        mRatingBar.setNumStars(3);
        mRatingBar.setStepSize(1);
        mRatingBar.setIsIndicator(true);
    }

    //位置交換
    private void Rand() {
        for (int i = 0; i < 1000; i++) {
            int n1 = (int) (Math.random() * 16);
            int n2 = (int) (Math.random() * 16);
            int temp = array[n1];
            array[n1] = array[n2];
            array[n2] = temp;
            String temp1 = str_array[n1];
            str_array[n1] = str_array[n2];
            str_array[n2] = temp1;
        }
    }

    //返回鍵Dialog的Builder
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
            ShowExit();
        }
        return true;
    }

    private void ShowExit() {
        mHandler = null;
        isExit.show();
    }

    //成功後的Dialog Messang
    private void ShowMessang(String str) {
        mHandler = null;
        new AlertDialog.Builder(this).setMessage(str)
                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setContentView(R.layout.trun_card_game_start);
                        StartSet();
//                        startActivity(new Intent(MultiplicationTableExam.this, menu_choose.class));
                        MultiplicationTableExam.this.finish();
                    }
                }).setCancelable(false).show();

    }

    //返回鍵Dialog的ClickListener
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    music.stop();
//                    startActivity(new Intent(MultiplicationTableExam.this, Choose_Mode.class));
                    MultiplicationTableExam.this.finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    mHandler = new Handler();
                    break;
                default:
                    break;
            }
        }
    };

    int t1, t2;
    //Button 點擊事件(判斷是否配對完)
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            int i = 0;
            String text = button.getText().toString();


            //第一次按
            if (text1.equals("")) {
                for (i = 0; i < str_array.length; i++) {
                    if (str_array[i].equals(text)) {
                        t1 = array[i];
                        break;
                    }
                }

                text1 = text;
                temp = button;
                temp.setBackgroundResource(R.drawable.turn_card_change);
                return;
            }
            for (i = 0; i < str_array.length; i++) {
                if (str_array[i].equals(text)) {
                    t2 = array[i];
                    break;
                }
            }
            Log.e("DEBUG", "t1: " + t1 + " t2:" + t2);
            //第二次按 判斷是否值相同、點同一個
            // text1.equals(button.getText().toString())
            if (t1 == t2 && temp != button) {

                temp.setVisibility(View.INVISIBLE);
                button.setVisibility(View.INVISIBLE);
                temp.setOnClickListener(null);
                button.setOnClickListener(null);
                count++;
                //完成後顯示成功
                if (count == size) {
                    SharedPreferences sharedPreferences = getSharedPreferences("User" , MODE_PRIVATE);
                    String User = sharedPreferences.getString("Name","");
                    timeup = System.currentTimeMillis();
                    totaltime = (timeup - startTime) / 1000;
                    int star= StarGrading.getStar(bundle.getString("Unit"),size,count);
                    FireBaseDataBaseTool.SendStudyRecord(bundle.getString("Unit")
                            ,User
                            ,"答對了" + count + "題," + "共花了" + totaltime + "秒,Star:"+star);
                    ShowMessang("答對了" + count + "題," + "共花了" + totaltime + "秒,Star:"+star);
                    mRatingBar.setRating(3);
                    music.stop();
                }
                temp = null;
                text1 = "";
                return;
            }
            //不同則恢復

            temp.setBackgroundResource(R.drawable.buttonshape);
            temp = null;
            text1 = "";

        }
    };
    Handler mHandler;

    //音樂暫停
    @Override
    protected void onPause() {
        super.onPause();
        mHandler = null;
        try {
            if (music.isPlaying())
                music.pause();
        } catch (Exception e) {
            Log.e("Debug", "TurnCardGame line 51");
            Log.e("Debug", e.toString());
        }

    }

}

