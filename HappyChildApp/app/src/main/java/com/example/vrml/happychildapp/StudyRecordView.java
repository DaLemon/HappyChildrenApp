package com.example.vrml.happychildapp;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vrml.happychildapp.AddSub.AddSub;
import com.example.vrml.happychildapp.HYC_Code.AdditionAndSubtraction.AdditionSubtractActivity;
import com.example.vrml.happychildapp.HYC_Code.MultiplicationTable.MultiplicationTable;
import com.example.vrml.happychildapp.HYC_Code.VideoView.TimeVideo;
import com.example.vrml.happychildapp.Homonym.Homonym;
import com.example.vrml.happychildapp.MatchGame.MatchGame;
import com.example.vrml.happychildapp.TimeGame.TimeGame;
import com.example.vrml.happychildapp.TurnCardGame.Turn_Card_Game;
import com.example.vrml.happychildapp.Turn_page.turn_page_pratice;
import com.example.vrml.happychildapp.Upload.AddSubUpload;
import com.example.vrml.happychildapp.Upload.HandUpload;
import com.example.vrml.happychildapp.Upload.HomonymUpload;
import com.example.vrml.happychildapp.Upload.MatchUpload;
import com.example.vrml.happychildapp.Upload.MultiplicationTableUpload;
import com.example.vrml.happychildapp.Upload.TimeUpload;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class StudyRecordView extends AppCompatActivity {
    private ListView listView;
    private UnitAdapter adapter;
    ArrayList<String> data;
    Bundle bundle;
    boolean isTeacher=false;
    HashMap<String,DataSnapshot> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_choose);
        map = new HashMap<String, DataSnapshot>();
        listView = (ListView) this.findViewById(R.id.unit_list_veiw);
        SharedPreferences Position =getSharedPreferences("Position",MODE_PRIVATE);
        if (Position.equals(isTeacher)){
            isTeacher=true;
        }

        data = new ArrayList<>();
        getdataFromFirebase();
    }
    private void getdataFromFirebase() {
        Intent intent = this.getIntent();
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("StudyRecord");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot temp : dataSnapshot.getChildren()) {
                    String result = "";
                    for (DataSnapshot temp2 : temp.getChildren()){
                        result+=temp2.getKey()+":";
                        for (DataSnapshot temp3:temp2.getChildren()){
//                            Log.e("DEBUG",temp3.getChildren().toString());
                             map.put(temp2.getKey().toString(),temp3);
                            Log.e("DEBUG",temp3+"");
                        }
                    }
                }
               ;
                setListView(new ArrayList<String>(map.keySet()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    boolean first = true;
    private void setListView( ArrayList data){
        adapter = new UnitAdapter(data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (first){
                    first=false;
                }else {
                    return;//應跳轉回menuChoose
                }
//                View view1 = LayoutInflater.from(StudyRecordView.this).inflate(R.layout.unit_choose_item, null);
                TextView textView = (TextView) view.findViewById(R.id.textView2);
                Log.e("DEBUG",textView.getText().toString());
                ArrayList<String> data = new ArrayList<String>();
                DataSnapshot result = map.get(textView.getText().toString());
                for(DataSnapshot temp:result.getChildren()){
                    Log.e("DEBUG",temp+"");
                    data.add(result.getKey()+":"+temp.getValue());
                }
                setListView(data);
            }
        });
    }

    private class UnitAdapter extends BaseAdapter {
        ArrayList<String> list;

        private UnitAdapter(ArrayList<String> data) {
            this.list = data;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view1 = LayoutInflater.from(StudyRecordView.this).inflate(R.layout.unit_choose_item, null);
            TextView textView = (TextView) view1.findViewById(R.id.textView2);
            textView.setText(list.get(i));
            return view1;
        }
    }
}

