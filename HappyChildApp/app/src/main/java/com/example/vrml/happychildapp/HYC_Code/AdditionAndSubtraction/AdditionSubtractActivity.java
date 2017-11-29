package com.example.vrml.happychildapp.HYC_Code.AdditionAndSubtraction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.vrml.happychildapp.HYC_Code.MultiplicationTable.MultiplicationTable;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.menu_choose;

public class AdditionSubtractActivity extends AppCompatActivity {
    AlertDialog isExit;
    DisplayMetrics metrics = new DisplayMetrics();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addition_subtraction);
        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        DialogSet();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        DisplayMetrics metrics = getResources().getDisplayMetrics();

        int DeviceTotalWidth = metrics.widthPixels;
        final Button add = (Button)findViewById(R.id.add1);
        Button add2 = (Button)findViewById(R.id.add2);
        Button minus1 = (Button)findViewById(R.id.minus1);
        Button minus2 = (Button)findViewById(R.id.minus2);

        add.setTextSize(DeviceTotalWidth/60);
        add2.setTextSize(DeviceTotalWidth/60);
        minus1.setTextSize(DeviceTotalWidth/60);
        minus2.setTextSize(DeviceTotalWidth/60);

        add.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent1 = new Intent(v.getContext(), addition1.class);
                        startActivity(myIntent1);
                        AdditionSubtractActivity.this.finish();
                    }
                }
        );
        add2.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent2 = new Intent(v.getContext(), addition2.class);
                        startActivity(myIntent2);
                        AdditionSubtractActivity.this.finish();
                    }
                }
        );
        minus1.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent3 = new Intent(v.getContext(), subtraction1.class);
                        startActivity(myIntent3);
                        AdditionSubtractActivity.this.finish();
                    }
                }
        );
        minus2.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent4 = new Intent(v.getContext(), subtraction2.class);
                        startActivity(myIntent4);
                        AdditionSubtractActivity.this.finish();
                    }
                }
        );

    }
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    startActivity(new Intent(AdditionSubtractActivity.this, menu_choose.class));
                    AdditionSubtractActivity.this.finish();
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
