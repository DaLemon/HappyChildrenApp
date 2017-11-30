package com.example.vrml.happychildapp.Jennifer_Code;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.vrml.happychildapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.itsrts.pptviewer.PPTViewer;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Created by Nora on 2017/11/21.
 */

public class pptShow extends AppCompatActivity {
    PPTViewer pptViewer;
    String FileName,TypeName;
    Bundle bundle;
    String subject,heading;
    String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pptshow);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        bundle = getIntent().getExtras();
        pptViewer = (PPTViewer) findViewById(R.id.pptViewer);
        pptViewer.setNext_img(R.drawable.next)
                .setPrev_img(R.drawable.prev)
                .setSettings_img(R.drawable.settings)
                .setZoomin_img(R.drawable.zoomin)
                .setZoomout_img(R.drawable.zoomout);
        heading = bundle.getString("Heading");
        subject = bundle.getString("Subject");
        FileName = bundle.getString("FileName");
        TypeName = bundle.getString("TypeName");

        if(subject.equals("Share")){
            path = "Public/"+bundle.getString("User")+"/"+FileName;
        }else {
            path = "Private/"+bundle.getString("User")+"/"+FileName;
        }
        DownloadFile(path);
    }
    File localFile;
    public void DownloadFile(String path){
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference temp = storageRef.child(path);

        Log.e("DEBUG","Line 66 temp "+temp);
        File rootPath = new File(Environment.getExternalStorageDirectory(), "/Download");
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }
        try {
            localFile = new File(rootPath,bundle.getString("FileName")+".ppt");
            Log.e("DEBUG","Line 75 localFile    "+localFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        temp.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                try {
//                    String filepath = Environment.getExternalStorageDirectory().getPath();
//                    filepath += "/Download/a.ppt";
                    String filepath = localFile+"";
                    pptViewer.loadPPT(pptShow.this,filepath);
                    Log.e("DEBUG","86 LocalFile    "+localFile.toString());

                } catch (Exception e) {
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
}
