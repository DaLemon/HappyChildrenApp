package com.example.vrml.happychildapp.HYC_Code.MultiplicationTable;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.TurnCardGame.Turn_Card_Game;
import com.example.vrml.happychildapp.menu_choose;

public class MultiplicationTable extends AppCompatActivity {
    AlertDialog isExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplicationtable);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        DialogSet();
        Button one = (Button)findViewById(R.id.one);
        one.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent1 = new Intent(v.getContext(), com.example.vrml.happychildapp.HYC_Code.MultiplicationTable.one.class);
                        startActivity(myIntent1);
                        MultiplicationTable.this.finish();
                    }
                }
        );

        Button two = (Button)findViewById(R.id.two);
        two.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent2 = new Intent(v.getContext(), two.class);
                        startActivity(myIntent2);
                        MultiplicationTable.this.finish();
                    }
                }
        );
        Button three = (Button)findViewById(R.id.three);
        three.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent3 = new Intent(v.getContext(), three.class);
                        startActivity(myIntent3);
                        MultiplicationTable.this.finish();
                    }
                }
        );
        Button four = (Button)findViewById(R.id.four);
        four.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent4 = new Intent(v.getContext(), four.class);
                        startActivity(myIntent4);
                        MultiplicationTable.this.finish();
                    }
                }
        );
        Button five = (Button)findViewById(R.id.five);
        five.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent5 = new Intent(v.getContext(), five.class);
                        startActivity(myIntent5);
                        MultiplicationTable.this.finish();
                    }
                }
        );
        Button six = (Button)findViewById(R.id.six);
        six.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent6 = new Intent(v.getContext(), six.class);
                        startActivity(myIntent6);
                        MultiplicationTable.this.finish();
                    }
                }
        );
        Button seven = (Button)findViewById(R.id.seven);
        seven.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent7 = new Intent(v.getContext(), seven.class);
                        startActivity(myIntent7);
                        MultiplicationTable.this.finish();
                    }
                }
        );
        Button eight = (Button)findViewById(R.id.eight);
        eight.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent8 = new Intent(v.getContext(), eight.class);
                        startActivity(myIntent8);
                        MultiplicationTable.this.finish();
                    }
                }
        );
        Button nine = (Button)findViewById(R.id.nine);
        nine.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent9 = new Intent(v.getContext(), nine.class);
                        startActivity(myIntent9);
                        MultiplicationTable.this.finish();
                    }
                }
        );

    }
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    startActivity(new Intent(MultiplicationTable.this, menu_choose.class));
                    MultiplicationTable.this.finish();
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
