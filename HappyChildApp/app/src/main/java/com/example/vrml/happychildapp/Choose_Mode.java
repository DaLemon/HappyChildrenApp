package com.example.vrml.happychildapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.example.vrml.happychildapp.Homonym.Homonym;
import com.example.vrml.happychildapp.TurnCardGame.Turn_Card_Game;
import com.example.vrml.happychildapp.Turn_page.turn_page_pratice;

public class Choose_Mode extends AppCompatActivity {
    private SoundPool soundPool;
    private int soundID;
    DisplayMetrics metrics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosemode);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Button to_turncard = (Button)findViewById(R.id.to_turncard);
        Button to_teaching = (Button)findViewById(R.id.to_teaching);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        soundID = soundPool.load(this,R.raw.dong,0);
        if(metrics.widthPixels > 2000) {
            to_turncard.setTextSize(metrics.widthPixels / 30);
            to_teaching.setTextSize(metrics.widthPixels / 30);
        }else {
            to_turncard.setTextSize(metrics.widthPixels / 60);
            to_teaching.setTextSize(metrics.widthPixels / 60);
        }
        to_teaching.setOnClickListener(ModeChoose);

        to_turncard.setOnClickListener(ModeChoose);

    }

    private View.OnClickListener ModeChoose = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            soundPool.play(soundID,1.0F,1.0F,0,0,1.0F);
            Intent intent = new Intent();
            Bundle bundle = Choose_Mode.this.getIntent().getExtras();
            if (v.getId()==R.id.to_teaching)
                bundle.putString("Mode","Teaching");
            else
                bundle.putString("Mode","Exam");
            intent.putExtras(bundle);
            intent.setClass(Choose_Mode.this,Choose_Unit.class);
            startActivity(intent);
            Choose_Mode.this.finish();
        }
    };
}
