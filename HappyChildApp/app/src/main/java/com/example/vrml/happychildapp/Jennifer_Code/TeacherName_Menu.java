package com.example.vrml.happychildapp.Jennifer_Code;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vrml.happychildapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TeacherName_Menu extends AppCompatActivity {
    private ListView listView;
    private UnitAdapter adapter;
    ArrayList<String> data;
    Bundle bundle;
    boolean isTeacher = false;
    DisplayMetrics metrics = new DisplayMetrics();
    HashMap<String, DataSnapshot> map;
    String User,FileName;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_choose);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        map = new HashMap<String, DataSnapshot>();
        listView = (ListView) this.findViewById(R.id.unit_list_veiw);
        SharedPreferences Position = getSharedPreferences("Position", MODE_PRIVATE);
        if (Position.equals(isTeacher)) {
            isTeacher = true;
        }

        data = new ArrayList<>();
        getdataFromFirebase();
    }

    private void getdataFromFirebase() {

        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Teach").child("Share");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot temp : dataSnapshot.getChildren()) {
                    map.put(temp.getKey().toString(), temp);
                    Log.e("DEBUG", "Line 62 map " + temp + "");
//
                }

                setListView(new ArrayList<String>(map.keySet()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    boolean first = true;

    private void setListView(ArrayList data) {
        adapter = new UnitAdapter(data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.textView2);

                if (first) {
                    first = false;
                } else {
                    FileName = textView.getText().toString();
                    String TypeName = map.get(User).child(FileName).getValue().toString();
                    bundle = new Bundle();
                    bundle.putString("Subject","Share");
                    bundle.putString("User",User);
                    bundle.putString("FileName",FileName);
                    bundle.putString("TypeName",TypeName);
                    startActivity(new Intent(TeacherName_Menu.this,DownloadView.class).putExtras(bundle));
                    TeacherName_Menu.this.finish();

                    return;
                }

                ArrayList<String> data = new ArrayList<String>();
                DataSnapshot result = map.get(textView.getText().toString());
                User = textView.getText().toString();

                Log.e("DEBUG", "User : " + textView.getText().toString());
                for (DataSnapshot temp : result.getChildren()) {
                    data.add(temp.getKey().toString());
                    Log.e("DEBUG", "UnitName  " + temp);
                }


//                String UserName=listView.getItemAtPosition(position).toString();
//                Log.e("DEBUG","Filename  "+UserName);
//                Intent intent = new Intent();
//                intent.setClass(TeacherName_Menu.this,showPPTview.class);
//                intent.putExtra("UserName",UserName);
//                startActivity(intent);

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
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            View view1 = LayoutInflater.from(TeacherName_Menu.this).inflate(R.layout.unit_choose_item, null);
            TextView textView = (TextView) view1.findViewById(R.id.textView2);
            textView.setText(list.get(i));
            textView.setTextSize(metrics.widthPixels / 60);
            return view1;
        }


    }

}