package com.example.vrml.happychildapp;


import android.content.Intent;
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
import android.widget.Toast;

import com.example.vrml.happychildapp.Jennifer_Code.SignInActivity;
import com.example.vrml.happychildapp.Jennifer_Code.SignOut;
import com.example.vrml.happychildapp.Jennifer_Code.StorageActivity;
import com.example.vrml.happychildapp.Jennifer_Code.TeacherName_Menu;
import com.example.vrml.happychildapp.Upload.Choose_Upload;

import java.util.ArrayList;

public class menu_choose extends AppCompatActivity {
    private ListView listView;
    private UnitAdapter adapter;
    private ArrayList<String> data;
    DisplayMetrics metrics;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_choose);
        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        listView = (ListView) findViewById(R.id.menu_listview);
        adapter = new menu_choose.UnitAdapter(setData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                Log.e("DEBUG","Line 40 ::"+view.getTag().toString());

                switch (view.getTag().toString()){
                    case "我的教學":
                        intent.setClass(menu_choose.this, Choose_Chinese_Math.class);

                        break;
                    case "學習紀錄":
                        intent.setClass(menu_choose.this,StudyRecordView.class);
                        break;
                    case "非共享教材區":
                        intent.setClass(menu_choose.this,TeacherName_Menu.class);
                        bundle.putString("Heading","Upload");
                        bundle.putString("Subject","Private");
                        break;
                    case "新增教材":
                        intent.setClass(menu_choose.this, StorageActivity.class);

                        break;
                    case "新增題庫":
                        intent.setClass(menu_choose.this, Choose_Upload.class);
                        break;
                    case "登出":
                        new SignOut();
                        intent.setClass(menu_choose.this, SignInActivity.class);

                        break;
                }
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private ArrayList setData() {
        //GET FROM SharedPreferences User
        String Position = getSharedPreferences("User" , MODE_PRIVATE).getString("Position","ERROR");
        data = new ArrayList<>();
        //CHECK REQUEST CODE
        if(Position.equals("ERROR")) {
            data.add("ERROR");
        }else {
            data.add("我的教學");//0
            data.add("學習紀錄");//1
            if(Position.equals("老師")){
                data.add("非共享教材區");//2
                data.add("新增教材");//3
                data.add("新增題庫");
            }
            data.add("登出");
        }
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
            View view1 = LayoutInflater.from(menu_choose.this).inflate(R.layout.menu_item, null);
            TextView textView = (TextView) view1.findViewById(R.id.menu_textView);
            view1.setTag(list.get(i));
            textView.setText(list.get(i));
            textView.setTextSize(metrics.widthPixels/50);
            return view1;
        }
    }
}
