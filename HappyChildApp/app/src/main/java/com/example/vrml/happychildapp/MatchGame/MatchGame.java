package com.example.vrml.happychildapp.MatchGame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vrml.happychildapp.Homonym.Homonym;
import com.example.vrml.happychildapp.Jennifer_Code.FireBaseDataBaseTool;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.StarGrading.StarGrading;
import com.example.vrml.happychildapp.TimeGame.TimeGame;
import com.example.vrml.happychildapp.menu_choose;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MatchGame extends AppCompatActivity {
    private TextView[] Phonetic = new TextView[5];
    private TextView[] Chinese = new TextView[5];
    private ImageView[] imageViews = new ImageView[5];

    private float p1X = 0, p1Y = 0, p2X = 0, p2Y = 0;
    private LinearLayout left, right;
    private boolean isWrong = false;
    private MotionEvent moveEvent;
    private int range = 0;//點的有效範圍
    MatchGameData matchGameData ;
    private long startTime, timeup, totaltime;

    final long ONE_MEGABYTE = 1024*1024;
    boolean finish = false;
    DrawPoint view,view2;
    Bundle bundle;
    AlertDialog isExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.match_game);
        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        DialogSet();
        left = (LinearLayout) findViewById(R.id.left);
        right = (LinearLayout) findViewById(R.id.right);
        startTime = System.currentTimeMillis();
        IDSet();
        view = new DrawPoint(this);
        view.setTag("1");
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.invalidate();
                return false;
            }
        });

        view2 = new DrawPoint(this);
        view2.setTag("2");
        view2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                view.invalidate();
                return false;
            }
        });
        left.addView(view);
        right.addView(view2);
        getdataFromFirebase();

        Button OK = (Button)findViewById(R.id.OK);
        OK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (!finish) {
                    view.checkAnswer();
                    view2.checkAnswer();
                    timeup = System.currentTimeMillis();
                    totaltime = (timeup - startTime) / 1000;
                    finish=true;
                }else {
                    SharedPreferences sharedPreferences = getSharedPreferences("User" , MODE_PRIVATE);
                    String User = sharedPreferences.getString("Name","");
                    timeup = System.currentTimeMillis();
                    totaltime = (timeup - startTime) / 1000;

                    int star= StarGrading.getStar(bundle.getString("Unit"),10,(view.getAns()+view2.getAns()));
                    ShowMessage("答對了" + (view.getAns()+view2.getAns()) + "題\n" + "共花了" + totaltime + "秒");
                    FireBaseDataBaseTool.SendStudyRecord(bundle.getString("Unit"),User,"答對了" + (view.getAns()+view2.getAns()) + "題," + "共花了" + totaltime + "秒,Star:"+star);

                }
            }
        });
    }

    private void IDSet() {
        for (int i = 0; i < 5; i++) {
            String tvP = "Phonetic" + (i + 1);
            String tvC = "Chinese" + (i + 1);
            String ims = "imageView" + (i + 1);
            int resPhoneticID = getResources().getIdentifier(tvP, "id", getPackageName());
            int resChineseID = getResources().getIdentifier(tvC, "id", getPackageName());
            int resImageID = getResources().getIdentifier(ims, "id", getPackageName());
            Phonetic[i] = ((TextView) findViewById(resPhoneticID));
            Chinese[i] = ((TextView) findViewById(resChineseID));
            imageViews[i] = ((ImageView) findViewById(resImageID));
        }

    }
    int ImageIndex = 0;

    public void Download(ImageView imageView){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference temp = storageRef.child(imageView.getTag().toString());
        temp.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap temp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                MatchGame.this.imageViews[ImageIndex++].setImageBitmap(temp);
                Log.e("DEBUG",""+ImageIndex+"   "+MatchGame.this.imageViews[ImageIndex-1].getTag());
                if (ImageIndex<5) {
                    Download(imageViews[ImageIndex]);
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("DEBUG",e.toString());
            }
        });
    }
    private void Rand(){
        String[] temp=matchGameData.getChineseData().clone();
        Log.e("DEBUG","140"+Arrays.asList(temp));
        Collections.shuffle(Arrays.asList(temp));
        matchGameData.setChineseData(temp);

        temp=matchGameData.getPhoneticData().clone();
        Collections.shuffle(Arrays.asList(temp));
        matchGameData.setPhoneticData(temp);

        temp=matchGameData.getImagePathData().clone();
        Log.e("DEBUG","149"+Arrays.asList(temp));
        Collections.shuffle(Arrays.asList(temp));
        Log.e("DEBUG","151"+Arrays.asList(temp));
        matchGameData.setImagePathData(temp);

        for (int i=0;i<matchGameData.getChineseData().length;i++){
            Log.e("DEBUG",matchGameData.getChineseData()[i]+"  "+matchGameData.getImagePathData()[i]+"  "+matchGameData.getPhoneticData()[i]);
        }
    }
    public void setData(MatchGameData matchGameData){
        Rand();
        for (int i = 0;i<5;i++){
            this.Chinese[i].setText(matchGameData.getNewChineseData()[i]);
            this.Phonetic[i].setText(matchGameData.getNewPhoneticData()[i]);
            String path = matchGameData.getNewImagePathData()[i];

            imageViews[i].setTag(path);
        }
        Download(imageViews[ImageIndex]);
    }
    private void getdataFromFirebase() {

        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Teach");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bundle = MatchGame.this.getIntent().getExtras();
                matchGameData=new MatchGameData(bundle,dataSnapshot);

                setData(matchGameData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public class DrawPoint extends View {
        private List<Float> mPoint = new ArrayList<>();
        private List<Integer>  wrong= new ArrayList<>();
        private Point[] dot = new Point[10];
        private boolean check = false;

        public DrawPoint(Context context) {
            super(context);
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Paint paint = new Paint();

            Paint line = new Paint();
            line.setStrokeWidth(12);
            line.setColor(Color.BLUE);
            paint.setColor(Color.GRAY);

            //寫for 畫點

            for (int i = 0; i < 10; i++) {
                canvas.drawCircle(dot[i].x, dot[i].y, 15, paint);
            }


            for (int i = 0; i < mPoint.size(); i += 4) {
                if (i + 4 <= mPoint.size())
                    canvas.drawLine(mPoint.get(i), mPoint.get(i + 1), mPoint.get(i + 2), mPoint.get(i + 3), line);
                //Log.e("mPoint","mPoint"+mPoint);
            }
            if (check && isTouch) {
                canvas.drawLine(p1X, p1Y, p2X, p2Y, line);
                // canvas.drawLine((float)21.1875,(float)913.60156,(float)467.3136,(float)1184.793,line);
//                Log.e("DEBUG", "check && isTouch" + check + " " + isTouch);
            }
            line.setColor(Color.RED);
            for (int i=0;i<wrong.size();i+=2){
                int wrong_i=wrong.get(i);
                int wrong_j=wrong.get(i+1);
                canvas.drawLine(dot[wrong_i].x,dot[wrong_i].y,dot[wrong_j].x,dot[wrong_j].y,line);
            }

        }

        //取得點圓心的位置
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            int width = getMeasuredWidth();//get Measure width and height
            int height = getMeasuredHeight();
            int startX = 0, startY = 0;

            startX = width / 25; //scale
            startY = height / 5 / 2;

            range = height / 5 / 4;

            for (int i = 0; i < 5; i++, startY += height / 5) {
                dot[i] = new Point(startX, startY);
                dot[i + 5] = new Point(startX + width * 25 / 28, startY);//慢慢兜出來的＝～＝
                //Log.e("DEBUG","X:"+dot[i].x+"  Y:"+dot[i].y);
            }
        }

        private boolean isTouch = false;//判斷是不是在dot的範圍內
        int down = 0;

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (isWrong) {
                return super.onTouchEvent(event);
            }
            moveEvent = MotionEvent.obtain(event);
            int checkpoint = isTouchPoint(event);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (checkpoint != -1) {
                        Log.d("DEBUG","check"+checkpoint);
                        down = checkpoint;
                        p1X = dot[checkpoint].x;
                        p1Y = dot[checkpoint].y;

                        invalidate();

                    }

                    break;
                case MotionEvent.ACTION_UP:
                    moveEvent = null;
                    check = false;
                    //只能左右連（左邊0~4，右邊5~9）
                    if (checkpoint != -1 && Math.abs(down/5-checkpoint/5)==1 ) {
                        p2X = dot[checkpoint].x;
                        p2Y = dot[checkpoint].y;
                        //是否為同一點
                        if (p1X == p2X && p1Y == p2Y)break;
                        boolean mPointCheck=true;

                        for (int i=0;i<mPoint.size();i+=2){
                            if(mPoint.get(i)==p1X&&mPoint.get(i+1)==p1Y){
                                mPointCheck=false;
                            }else if (mPoint.get(i)==p2X&&mPoint.get(i+1)==p2Y){
                                mPointCheck=false;
                            }
                        }
                        if (mPointCheck) {
                            mPoint.add(p1X);
                            mPoint.add(p1Y);
                            mPoint.add(p2X);
                            mPoint.add(p2Y);
                        }


                        invalidate();
                    }

                    isTouch = false;
                    down = -1;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (isTouch == true) {
                        check = true;

                        p2X = event.getX();
                        p2Y = event.getY();

                        invalidate();
                    }
                    break;
                default:
                    return super.onTouchEvent(event);
            }
            return true;
        }

        //判斷觸點是否在有效範圍內
        public int isTouchPoint(MotionEvent event) {

            for (int i = 0; i < dot.length; i++) {
                Point point = dot[i];
                if (event.getX() <= point.x + range &&
                        event.getX() >= point.x - range &&
                        event.getY() <= point.y + range &&
                        event.getY() >= point.y - range) {
                    isTouch = true;
                    return i;//回傳第幾個點
                }
            }

            return -1;
        }
        public int Ans=0;

        public int getAns(){return Ans;}

        public void checkAnswer(){

            String[] str=matchGameData.getNewChineseData();
            String MODE = "Chinese";
            if (this.getTag().equals("2")){
                MODE="Phonetic";
                str = matchGameData.getNewPhoneticData();
            }
            for (int i=0;i<mPoint.size();i+=4){
                int check1 = getdot(mPoint.get(i+0),mPoint.get(i+1));
                int check2 = getdot(mPoint.get(i+2),mPoint.get(i+3));
                if (this.getTag().equals("1")) {
                    if (check1 > check2) {
                        int temp = check1;
                        check1 = check2;
                        check2 = temp;
                    }check2=check2%5;
                }else {
                    if (check1 < check2) {
                        int temp = check1;
                        check1 = check2;
                        check2 = temp;
                    }check1=check1%5;
                }

                int right=matchGameData.compare(str[check1], imageViews[check2].getTag().toString(),MODE);
                Log.e("DEBUG","Match_Game353:   "+right);
                if (right==-1){
                    Ans++;

                }else {
                    if (this.getTag().equals("2")){
                        int temp = right;
                        right = check2;
                        check2 = temp;
                    }
                    wrong.add(right);
                    wrong.add(check2+5);

                }
            }
            invalidate();
        }
        public int getdot(float x,float y){
            for (int i=0;i<dot.length;i++) {
                if (x == dot[i].x && y == dot[i].y)
                    return i;
            }
            return -1;
        }

    }
    private void ShowMessage(String str) {
        new AlertDialog.Builder(this).setMessage(str)
                .setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        startActivity(new Intent(MatchGame.this, menu_choose.class));
                        MatchGame.this.finish();
                    }
                }).setCancelable(false).show();

    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
//                    startActivity(new Intent(MatchGame.this, menu_choose.class));
                    MatchGame.this.finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:

                    break;
                default:
                    break;
            }
        }
    };
    private void DialogSet() {
        isExit = new AlertDialog.Builder(this)
                .setTitle("離開")
                .setMessage("確定要退出嗎?")
                .setPositiveButton("Yes", listener)
                .setNegativeButton("No", listener)
                .setCancelable(false)
                .create();
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            isExit.show();
        }
        return true;
    }

}
