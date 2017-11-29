package com.example.vrml.happychildapp.HYC_Code.VideoView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.vrml.happychildapp.HYC_Code.AdditionAndSubtraction.AdditionSubtractActivity;
import com.example.vrml.happychildapp.MatchGame.MatchGame;
import com.example.vrml.happychildapp.MatchGame.MatchGameData;
import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.menu_choose;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by Nora on 2017/11/2.
 */

public class TimeVideo extends AppCompatActivity implements MediaController.MediaPlayerControl{
    private VideoView vidView;
    private MediaController vidControl;
    AlertDialog isExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);
        vidControl = new MediaController(this);
        vidView = (VideoView) findViewById(R.id.videoView);
        vidView.setMediaController(vidControl);
        DialogSet();
        String path = this.getIntent().getExtras().getString("Lesson");
        getdataFromFirebase();
    }
    private void getdataFromFirebase() {

        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Teach");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Bundle bundle = TimeVideo.this.getIntent().getExtras();
                String Subject = bundle.getString("Subject");
                String Mode = bundle.getString("Mode");
                String Unit = bundle.getString("Unit");
                String Lesson = bundle.getString("Lesson");
                dataSnapshot = dataSnapshot.child(Subject).child(Mode).child(Unit).child(Lesson);
                DownloadFile(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    File localFile;
    public void DownloadFile(String path){
        Log.e("DEBUG","Path "+path);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference temp = storageRef.child(path);
        Pattern pattern = Pattern.compile("[./]");
        String[] PathSplit = pattern.split(path);
        int size = PathSplit.length;
        String FileName = PathSplit[size-2];//裁切檔名
        String TypeName = PathSplit[size-1];
        try {
            localFile = File.createTempFile(FileName, TypeName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        temp.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Dialog miss
                try {
                    vidView.setVideoPath(localFile.getCanonicalPath());//取得路徑
                    vidView.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Dialog Error
            }
        });
    }

    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    startActivity(new Intent(TimeVideo.this, menu_choose.class));
                    TimeVideo.this.finish();
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

    @Override
    public void start() {
        //vidView.start();
    }

    @Override
    public void pause() {
        if(vidView.isPlaying()){
            vidView.pause();
        }
    }

    @Override
    public int getDuration() {
        return vidView.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return vidView.getCurrentPosition();
    }

    @Override
    public void seekTo(int pos) {
        vidView.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        return vidView.isPlaying();
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return vidView.canPause();
    }

    @Override
    public boolean canSeekBackward() {
        return vidView.canSeekBackward();
    }

    @Override
    public boolean canSeekForward() {
        return vidView.canSeekForward();
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
}
