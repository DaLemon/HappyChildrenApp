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

import com.example.vrml.happychildapp.Homonym.Homonym;
import com.example.vrml.happychildapp.MatchGame.MatchGame;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Choose_Lesson extends AppCompatActivity {
    private ListView listView;
    private UnitAdapter adapter;
     ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_choose);

        listView = (ListView) this.findViewById(R.id.unit_list_veiw);
        getdataFromFirebase();
    }
    private void getdataFromFirebase() {

        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Teach");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                data = new ArrayList<>();
                dataSnapshot = dataSnapshot.child("Chinese").child("Exam").child("Homonym");
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

                Bundle bundle = new Bundle();
                bundle.putString("Lesson",Choose_Lesson.this.data.get(i));
                startActivity(new Intent(Choose_Lesson.this,Choose_Mode.class).putExtras(bundle));

                Log.e("DEBUG","LESSON"+Choose_Lesson.this.data.get(i));
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

