package com.example.vrml.happychildapp.Jennifer_Code;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pptshow);

        bundle = getIntent().getExtras();
        pptViewer = (PPTViewer) findViewById(R.id.pptViewer);
        pptViewer.setNext_img(R.drawable.next)
                .setPrev_img(R.drawable.prev)
                .setSettings_img(R.drawable.settings)
                .setZoomin_img(R.drawable.zoomin)
                .setZoomout_img(R.drawable.zoomout);
        String test = "Public/"+bundle.getString("User")+"/"+bundle.getString("FileName");
        FileName = bundle.getString("FileName");
        TypeName = bundle.getString("TypeName");
        Log.e("DEBUG","Test "+test);
        DownloadFile(test);
    }
    File localFile;
    public void DownloadFile(String path){
        Log.e("DEBUG","Path "+path);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference temp = storageRef.child(path);
        File rootPath = new File(Environment.getExternalStorageDirectory(), "/Download");
        Log.e("DEBUG","54 rootPath "+rootPath);
        if(!rootPath.exists()) {
            rootPath.mkdirs();
        }
        try {
            localFile = new File(rootPath,bundle.getString("FileName"));
            Log.e("DEBUG","60 localFile"+localFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        temp.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                // Dialog miss
                try {
//                    String filepath = Environment.getExternalStorageDirectory().getPath();
//                    filepath += "/Download/a.ppt";
                    String filepath = localFile+".ppt";
                    pptViewer.loadPPT(pptShow.this,filepath);
                    Log.e("DEBUG","LocalFile    "+filepath);


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
