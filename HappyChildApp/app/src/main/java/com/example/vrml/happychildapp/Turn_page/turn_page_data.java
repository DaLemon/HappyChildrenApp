package com.example.vrml.happychildapp.Turn_page;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;

import com.example.vrml.happychildapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nora on 2017/11/2.
 */

public class turn_page_data {
    DataSnapshot dataSnapshot;
    List<Bitmap> mBitmaps = new ArrayList<>();
    int count = 0;
    final long ONE_MEGABYTE = Long.MAX_VALUE;

    public turn_page_data(Bundle bundle, DataSnapshot dataSnapshot){
        String Subject = bundle.getString("Subject");
        String Mode = bundle.getString("Mode");
        String Unit = bundle.getString("Unit");
        String Lesson = bundle.getString("Lesson");
        dataSnapshot = dataSnapshot.child(Subject).child(Mode).child(Unit).child(Lesson);
        this.dataSnapshot = dataSnapshot;
        count = (int) dataSnapshot.getChildrenCount();
        for (DataSnapshot temp : dataSnapshot.getChildren()) {
            Download(temp.getValue().toString());
        }
    }
    public void Download(String path){
        String[] temp2 = path.split("/");
        int size = temp2.length;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference(temp2[size-2]);
        StorageReference temp = storageRef.child(temp2[size-1]);
        temp.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap temp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                mBitmaps.add(temp);
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("DEBUG",e.toString());
            }
        });
    }

    public List<Bitmap> getImageData(){return mBitmaps;}
}
