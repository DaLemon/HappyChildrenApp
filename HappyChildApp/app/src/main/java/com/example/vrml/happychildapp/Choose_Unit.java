package com.example.vrml.happychildapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vrml.happychildapp.Homonym.Homonym;
import com.example.vrml.happychildapp.MatchGame.MatchGame;

import java.util.ArrayList;


public class Choose_Unit extends AppCompatActivity {
    private ListView listView;
    private UnitAdapter adapter;
    private ArrayList<String> data;
    ArrayList<String> firebaseData = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_choose);

        listView = (ListView) this.findViewById(R.id.unit_list_veiw);
        adapter = new UnitAdapter(setData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();

                switch (i){
                    case 0:
                        firebaseData.add("Chinese");
                        firebaseData.add("Exam");
                        firebaseData.add("Hand");

                        break;
                    case 1:
                        firebaseData.add("Chinese");
                        firebaseData.add("Exam");
                        firebaseData.add("Homonym");

                        break;
                    case 2:
                        firebaseData.add("Chinese");
                        firebaseData.add("Exam");
                        firebaseData.add("Match");

                        break;
                }
                intent.putExtra("path",firebaseData);
                intent.setClass(Choose_Unit.this, Choose_Lesson.class);
                startActivity(intent);
                Choose_Unit.this.finish();

            }
        });
    }

    private ArrayList setData() {
        data = new ArrayList<>();
        data.add("部首識字");//0
        data.add("同音異字");//1
        data.add("國字注音");//2
        return data;
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
            View view1 = LayoutInflater.from(Choose_Unit.this).inflate(R.layout.unit_choose_item, null);
            TextView textView = (TextView) view1.findViewById(R.id.textView2);
            textView.setText(list.get(i));
            return view1;
        }
    }
}

