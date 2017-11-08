package com.example.vrml.happychildapp.Turn_page;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.vrml.happychildapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by VRML on 2017/3/7.
 */

public class turn_page_pratice extends AppCompatActivity {
    ProgressDialog dialog ;
    List<Bitmap> mBitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_layout);
        dialog = ProgressDialog.show(turn_page_pratice.this, "讀取中", "資料下載中...",true);
        mBitmaps = new ArrayList<>();
        getdataFromFirebase();
    }
    long size;
    private void getdataFromFirebase() {
        DatabaseReference reference_contacts = FirebaseDatabase.getInstance().getReference("Teach");
        reference_contacts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Bundle bundle = turn_page_pratice.this.getIntent().getExtras();
                String Subject = bundle.getString("Subject");
                String Mode = bundle.getString("Mode");
                String Unit = bundle.getString("Unit");
                String Lesson = bundle.getString("Lesson");
                dataSnapshot = dataSnapshot.child(Subject).child(Mode).child(Unit).child(Lesson);
                size = dataSnapshot.getChildrenCount();
                Log.e("DEBUG","turnPratice Line 56 "+size);
                for (DataSnapshot temp : dataSnapshot.getChildren()) {
                    String[] temp2 = temp.getValue().toString().split("/");
                    final int size = temp2.length;
                    StorageReference storageRef = FirebaseStorage.getInstance().getReference(temp2[size-2]);
                    StorageReference temp3 = storageRef.child(temp2[size-1]);
//        Log.e("DEBUG","turnData line 46 : "+temp.toString());
                    temp3.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap temp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                            mBitmaps.add(temp);
                            Log.e("DEBUG","Fuckkkkk "+mBitmaps.size());
                            if(mBitmaps.size() == turn_page_pratice.this.size){
                                setContentView(new turnView(turn_page_pratice.this,mBitmaps));
                                dialog.dismiss();
                            }
                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e("DEBUG",e.toString());
                        }
                    });

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
