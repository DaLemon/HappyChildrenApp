package com.example.vrml.happychildapp.Upload;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.vrml.happychildapp.Choose_Chinese_Math;
import com.example.vrml.happychildapp.Jennifer_Code.SignInActivity;
import com.example.vrml.happychildapp.Jennifer_Code.SignOut;
import com.example.vrml.happychildapp.Jennifer_Code.StorageActivity;
import com.example.vrml.happychildapp.Jennifer_Code.TeacherName_Menu;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.StudyRecordView;

import java.util.ArrayList;

/**
 * Created by Nora on 2017/11/29.
 */

public class Choose_Upload extends AppCompatActivity {
    private ListView listView;
    private Choose_Upload.UnitAdapter adapter;
    private ArrayList<String> data;
    DisplayMetrics metrics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_choose);
        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        listView = (ListView) findViewById(R.id.menu_listview);
        adapter = new Choose_Upload.UnitAdapter(setData());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                Log.e("DEBUG", "Line 40 ::" + view.getTag().toString());

                switch (view.getTag().toString()) {
                    case "部首識字":
                        intent.setClass(Choose_Upload.this, HandUpload.class);
                        bundle.putString("Subject", "Chinese");
                        bundle.putString("Mode","Exam");
                        bundle.putString("Unit","Hand");
                        break;
                    case "同音異字":
                        intent.setClass(Choose_Upload.this, HomonymUpload.class);
                        bundle.putString("Subject", "Chinese");
                        bundle.putString("Mode","Exam");
                        bundle.putString("Unit","Homonym");
                        break;
                    case "國字注音":
                        intent.setClass(Choose_Upload.this, MatchUpload.class);
                        bundle.putString("Subject", "Chinese");
                        bundle.putString("Mode","Exam");
                        bundle.putString("Unit","Match");
                        break;
                    case "加減法":
                        intent.setClass(Choose_Upload.this, AddSubUpload.class);
                        bundle.putString("Subject", "Math");
                        bundle.putString("Mode","Exam");
                        bundle.putString("Unit","AddSub");
                        break;
                    case "九九乘法":
                        intent.setClass(Choose_Upload.this, MultiplicationTableUpload.class);
                        bundle.putString("Subject", "Math");
                        bundle.putString("Mode","Exam");
                        bundle.putString("Unit","MultiplicationTable");

                        break;
                    case "認識時間":
                        intent.setClass(Choose_Upload.this, TimeUpload.class);
                        bundle.putString("Subject", "Math");
                        bundle.putString("Mode","Exam");
                        bundle.putString("Unit","TimeVideo");
                        break;
                }
                intent.putExtras(bundle);
                startActivity(intent);
                Choose_Upload.this.finish();
            }
        });
    }

    private ArrayList setData() {
        //GET FROM SharedPreferences User
        String Position = getSharedPreferences("User", MODE_PRIVATE).getString("Position", "ERROR");
        data = new ArrayList<>();
        //CHECK REQUEST CODE
        if (Position.equals("ERROR")) {
            data.add("ERROR");
        } else {
            data.add("部首識字");//0
            data.add("同音異字");//1
            data.add("國字注音");//2

            data.add("加減法");//3
            data.add("九九乘法");//4
            data.add("認識時間");//5
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
            View view1 = LayoutInflater.from(Choose_Upload.this).inflate(R.layout.menu_item, null);
            TextView textView = (TextView) view1.findViewById(R.id.menu_textView);
            view1.setTag(list.get(i));
            textView.setText(list.get(i));
            textView.setTextSize(metrics.widthPixels / 50);
            return view1;
        }
    }
}
