package com.example.vrml.happychildapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by VRML on 2017/5/23.
 */

public class Choose_Chinese_Math extends AppCompatActivity {
    private ListView listView;
    private UnitAdapter adapter;
    private ArrayList<String> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unit_choose);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        listView = (ListView) this.findViewById(R.id.unit_list_veiw);
        adapter = new UnitAdapter(setData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Choose_Chinese_Math.this,Choose_Mode.class);
                Bundle bundle = Choose_Chinese_Math.this.getIntent().getExtras();
                if (i == 0)
                    bundle.putString("Subject","Chinese");
                else
                    bundle.putString("Subject","Math");
                intent.putExtras(bundle);
                startActivity(intent);
                Choose_Chinese_Math.this.finish();
            }
        });
    }

    private ArrayList setData() {
        data = new ArrayList<>();
        data.add("國文");
        data.add("數學");
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
            View view1 = LayoutInflater.from(Choose_Chinese_Math.this).inflate(R.layout.unit_choose_item, null);
            TextView textView = (TextView) view1.findViewById(R.id.textView2);
            textView.setText(list.get(i));
            return view1;
        }
    }
}
