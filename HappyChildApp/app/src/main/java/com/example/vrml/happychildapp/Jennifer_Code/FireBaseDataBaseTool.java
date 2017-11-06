package com.example.vrml.happychildapp.Jennifer_Code;

import android.content.SharedPreferences;
import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import android.content.SharedPreferences;


/**
 * Created by VRML on 2017/10/13.
 */

public class FireBaseDataBaseTool {

    public static void SendText(String Path, Object message) {
        FirebaseDatabase mFireBaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = mFireBaseDatabase.getReference();
        databaseReference.child(Path).setValue(message);
    }

    public static void SendText(List<String> Path, Object message) {
        FirebaseDatabase mFireBaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = mFireBaseDatabase.getReference();
        for (String temp : Path) {
            databaseReference = databaseReference.child(temp);
        }
        databaseReference.setValue(message);
    }

    static StorageReference databaseReference;
    public static void SendStorage(String path, String filename, Uri filepath) {

        databaseReference = FirebaseStorage.getInstance().getReference();
        databaseReference = databaseReference.child(path).child(filename);
        databaseReference.putFile(filepath);
    }

    public static void SendStorage(String path, String filename, Uri filepath,
                                   OnSuccessListener<UploadTask.TaskSnapshot> success, OnFailureListener fail) {
        databaseReference = FirebaseStorage.getInstance().getReference();
        databaseReference = databaseReference.child(path).child(filename);
        databaseReference.putFile(filepath).addOnSuccessListener(success).addOnFailureListener(fail);
    }

    public static StorageReference GetDatabaseReference() {
        return databaseReference;
    }

    public static List<String> GetUserPath(){
        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        List<String> UserPath = new ArrayList<>() ;
        UserPath.add("StudyRecord");
        UserPath.add(Uid);
        return UserPath;
    }
    public static void SendStudyRecord(String Unit,String user, Object message ){
        String Uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        List<String> UserPath = new ArrayList<>() ;
        UserPath.add("StudyRecord");
        UserPath.add(Uid);
        UserPath.add(user);
        UserPath.add(Unit);
        UserPath.add(System.currentTimeMillis()+"");
        SendText(UserPath,message);
    }
}
