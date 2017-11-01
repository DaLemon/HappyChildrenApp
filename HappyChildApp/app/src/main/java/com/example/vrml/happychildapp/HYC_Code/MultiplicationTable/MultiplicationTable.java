package com.example.vrml.happychildapp.HYC_Code.MultiplicationTable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.vrml.happychildapp.R;

public class MultiplicationTable extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiplicationtable);

        Button one = (Button)findViewById(R.id.one);
        one.setOnClickListener(
                new Button.OnClickListener()
                {
                    public void onClick(View v)
                    {
                        Intent myIntent1 = new Intent(v.getContext(), com.example.vrml.happychildapp.HYC_Code.MultiplicationTable.one.class);
                        startActivity(myIntent1);
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
                    }
                }
        );

    }
}
