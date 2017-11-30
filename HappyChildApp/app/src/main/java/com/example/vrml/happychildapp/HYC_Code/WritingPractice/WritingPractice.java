package com.example.vrml.happychildapp.HYC_Code.WritingPractice;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vrml.happychildapp.R;

public class WritingPractice extends AppCompatActivity {

    private CanvasView canvasView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.writing);
        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        canvasView = (CanvasView)findViewById(R.id.canvas);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            canvasView.setBackground(getResources().getDrawable(R.drawable.write_new));
        }
    }

    public void  clearCanvas(View v){
        canvasView.clearCanvas();
    }
}
