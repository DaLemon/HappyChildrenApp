package com.example.vrml.happychildapp;


import android.content.Intent;
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
import com.example.vrml.happychildapp.MultiplicationTable.MultiplicationTableExam;
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
import java.util.HashMap;


public class Choose_Lesson extends AppCompatActivity {
    private ListView listView;
    private UnitAdapter adapter;
    ArrayList<String> data;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_choose);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        listView = (ListView) this.findViewById(R.id.unit_list_veiw);
        bundle = Choose_Lesson.this.getIntent().getExtras();
        if(bundle.getString("Modify","").equals("Y")){
            HashMap<String,Class<?>> map = new HashMap<String, Class<?>>();
            map.put("Hand", HandUpload.class);
            map.put("Homonym", HomonymUpload.class);
            map.put("Match", MatchUpload.class);
            map.put("AddSub", AddSubUpload.class);
            map.put("MultiplicationTable",MultiplicationTableUpload.class);
            map.put("TimeVideo", TimeUpload.class);

            Class<?> cls=map.get(bundle.getString("Unit"));
//            Log.e("DEBUG","58  "+bundle.getString("Unit"));
            startActivity(new Intent(Choose_Lesson.this,cls).putExtras(bundle));
            Choose_Lesson.this.finish();
        }else {
            getdataFromFirebase();
        }
    }
    private void getdataFromFirebase() {
        Intent intent = this.getIntent();
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Teach");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = new ArrayList<>();
                dataSnapshot = dataSnapshot.child(bundle.getString("Subject")).child(bundle.getString("Mode")).child(bundle.getString("Unit"));
                for (DataSnapshot temp : dataSnapshot.getChildren()) {
                    data.add(temp.getKey());

                }
                setListView(data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void setListView( ArrayList data){
        adapter = new UnitAdapter(data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                bundle.putString("Lesson",Choose_Lesson.this.data.get(i));
                HashMap<String,Class<?>> map = new HashMap<String, Class<?>>();

                if(bundle.getString("Mode").equals("Exam")){
                    map.put("Hand",Turn_Card_Game.class);
                    map.put("Homonym",Homonym.class);
                    map.put("Match",MatchGame.class);
                    map.put("AddSub", AddSub.class);
                    map.put("MultiplicationTable", MultiplicationTableExam.class);
                    map.put("TimeVideo",TimeGame.class);
                    Log.e("DEBUG","Lesson Line 87");
                }else if (bundle.getString("Mode").equals("Teaching")){
                    map.put("AddSub", AdditionSubtractActivity.class);
                    map.put("MultiplicationTable", MultiplicationTable.class);
                    map.put("TimeVideo", TimeVideo.class);
                    map.put("Hand", turn_page_pratice.class);
                    map.put("Homonym",turn_page_pratice.class);
                    map.put("Match",turn_page_pratice.class);
                    Log.e("DEBUG","Lesson Line 95");
                }
                Class<?> cls=map.get(bundle.getString("Unit"));
                Log.e("DEBUG","Line87:"+bundle.getString("Unit"));


                startActivity(new Intent(Choose_Lesson.this,cls).putExtras(bundle));
                Choose_Lesson.this.finish();
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
            View view1 = LayoutInflater.from(Choose_Lesson.this).inflate(R.layout.unit_choose_item, null);
            TextView textView = (TextView) view1.findViewById(R.id.textView2);
            textView.setText(list.get(i));
            return view1;
        }
    }
}

