package com.example.vrml.happychildapp.Homonym;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vrml.happychildapp.Jennifer_Code.FireBaseDataBaseTool;
import com.example.vrml.happychildapp.Jennifer_Code.ProfileActivity;
import com.example.vrml.happychildapp.Jennifer_Code.SignInActivity;
import com.example.vrml.happychildapp.Jennifer_Code.UserInformation;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.menu_choose;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Homonym extends AppCompatActivity {
    TextView questiontext;
    ImageView questionImage;
    Button option1,option2,option3;
    DisplayMetrics metrics = new DisplayMetrics();
    List<String> title = new ArrayList<String>();
    List<String[]> answer = new ArrayList<String[]>();
    String temp2;

    private int index = 0;
    private long startTime,timeup,totaltime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homonym);
        questiontext = (TextView) findViewById(R.id.question);
        questionImage = (ImageView) findViewById(R.id.questionImage);
        option1 = (Button) findViewById(R.id.option1);
        option2 = (Button) findViewById(R.id.option2);
        option3 = (Button) findViewById(R.id.option3);
        option1.setOnClickListener(click);
        option2.setOnClickListener(click);
        option3.setOnClickListener(click);
        size();
        getdataFromFirebase();


        startTime = System.currentTimeMillis();
    }



    private void size(){
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        questiontext.setTextSize(metrics.widthPixels/40);
        option1.setTextSize(metrics.widthPixels/40);
        option2.setTextSize(metrics.widthPixels/40);
        option3.setTextSize(metrics.widthPixels/40);
    }
    private void getdataFromFirebase(){
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Teach");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int c=0;
                dataSnapshot = dataSnapshot.child("Chinese").child("Exam").child("Homonym");
                for(DataSnapshot temp:dataSnapshot.getChildren()) {

                    temp2 = (String) temp.getValue();
                    Log.e("DEBUG","before"+temp2);
                    title.add(temp2.split(",")[0]);
                    Log.e("DEBUG","title"+title+"");
                    answer.add(new String[]{temp2.split(",")[1]+"",temp2.split(",")[2]+"",temp2.split(",")[3]+""});
                    Log.e("DEBUG",temp2.split(",")[1]+"");

                }
                setData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setData(){
        questiontext.setText(title.get(index));
        option1.setText(answer.get(index)[0]);
        option2.setText(answer.get(index)[1]);
        option3.setText(answer.get(index++)[2]);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(index < title.size()) {
                setData();
            }else{
                timeup = System.currentTimeMillis();
                totaltime = (timeup-startTime)/1000;
                Toast.makeText(Homonym.this,totaltime+"", Toast.LENGTH_LONG).show();
                Toast.makeText(Homonym.this,"END", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
