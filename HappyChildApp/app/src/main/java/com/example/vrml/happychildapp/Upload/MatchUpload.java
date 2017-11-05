package com.example.vrml.happychildapp.Upload;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vrml.happychildapp.menu_choose;
import com.example.vrml.happychildapp.Jennifer_Code.FireBaseDataBaseTool;
import com.example.vrml.happychildapp.MatchGame.MatchGame;
import com.example.vrml.happychildapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nora on 2017/11/5.
 */

public class MatchUpload extends AppCompatActivity {
    private static final int PICKFILE_RESULT_CODE = 1;
    Button submit;
    EditText NameText;
    ImageView[] imageView;
    int index=0;
    private Uri[] filepath = new Uri[5];
    private StorageReference StorageRef;
    String[] result = new String[5];
    String[] imagepath = new String[5];
    ProgressDialog progressDialog;
    Bundle bundle;
    EditText title;
    List<String> path;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchupload);
        submit = (Button)findViewById(R.id.matchSubmit);
        title = (EditText) findViewById(R.id.matchTitle);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        setImageView();

        StorageRef = FirebaseStorage.getInstance().getReference();
        bundle = getIntent().getExtras();
        path = new ArrayList<String>();
        path.add("Teach");
        path.add(bundle.getString("Subject"));
        path.add(bundle.getString("Mode"));
        path.add(bundle.getString("Unit"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText chineseEditText;
                EditText phoneticEditText;
                path.add(title.getText().toString());
                for (int i = 0; i < 5; i++) {
                    path.add("content"+(i+1));
                    String chinese = "ChineseEditText"+(i+1);
                    String phonetic = "PhoneticEditText"+(i+1);

                    int chineseID = getResources().getIdentifier(chinese, "id", getPackageName());
                    int phoneticID = getResources().getIdentifier(phonetic, "id", getPackageName());
                    chineseEditText = (EditText)findViewById(chineseID);
                    phoneticEditText = (EditText)findViewById(phoneticID);
                    result[i] = "";
                    result[i]+= chineseEditText.getText()+";";
                    result[i]+= phoneticEditText.getText()+";";
                    result[i]+= "MatchGame/"+chineseEditText.getText()+imagepath[i];
                    imagepath[i] = chineseEditText.getText()+imagepath[i];
                    Log.e("DEBUG","Line 87 "+path+"\n"+result[i]);
                    FireBaseDataBaseTool.SendText(path,result[i]);
                    path.remove(path.size()-1);
                }
                fileupload();

            }
        });
    }
    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ImageView image = (ImageView) v;
            Log.e("DEBUG","MatchUpload 78:"+image.getTag().toString());
            index=Integer.parseInt(image.getTag().toString());
            showfilechooser();
        }
    };
    public void setImageView(){
        imageView = new ImageView[5];
        for (int i=0;i<5;i++){
            String image = "matchImageView"+(i+1);
            int imageID = getResources().getIdentifier(image, "id", getPackageName());
            Log.e("DEBUG","imageID : "+imageID);
            imageView[i] = (ImageView)findViewById(imageID);
            imageView[i].setOnClickListener(onClick);
            imageView[i].setTag(i+"");
        }
    }
    private void showfilechooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent,"Select the PPT"),PICKFILE_RESULT_CODE);
    }
    private void fileupload(){
        index = 0;
        progressDialog.show();
        FireBaseDataBaseTool.SendStorage("MatchGame",imagepath[index],filepath[index],successListener,failureListener);
    }
    OnSuccessListener<UploadTask.TaskSnapshot> successListener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            if (index<5) {
                FireBaseDataBaseTool.SendStorage("MatchGame",imagepath[index],filepath[index],successListener,failureListener);
                index++;
            }else {
                progressDialog.dismiss();
                Intent intent = new Intent(MatchUpload.this,menu_choose.class);
                startActivity(intent);
            }
        }
    };
    OnFailureListener failureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.e("DEBUG","ERRRRRRR"+e.toString());
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== PICKFILE_RESULT_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filepath[index]=data.getData();

            String temp = filepath[index].toString();
            temp = temp.substring(temp.lastIndexOf("."));
            imagepath[index] = temp;
            Log.e("DEBUG","Matchupload 149 : "+temp);
            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath[index]);
                imageView[index].setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}