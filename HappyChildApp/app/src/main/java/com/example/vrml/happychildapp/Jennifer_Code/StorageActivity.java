package com.example.vrml.happychildapp.Jennifer_Code;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vrml.happychildapp.R;
import com.example.vrml.happychildapp.menu_choose;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.regex.Pattern;

public class StorageActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PICKFILE_RESULT_CODE = 1;
    private ImageView image;
    Button choose,upload;
    Button show;
    EditText NameText;
    private Uri filepath;
    private StorageReference StorageRef;
    CheckBox Share;
    String Filename;
    String[] choosePath;
    Bundle bundle;
    Intent intent;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Upload");
    boolean isEmpty= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        getSupportActionBar().hide(); //隱藏標題
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        StorageRef = FirebaseStorage.getInstance().getReference();

        image=(ImageView)findViewById(R.id.image);
        choose=(Button)findViewById(R.id.choose);
        upload=(Button)findViewById(R.id.upload);
        NameText = (EditText) findViewById(R.id.Name);
        Share=(CheckBox) findViewById(R.id.recycle_share_checkBox);

        choose.setOnClickListener(this);
        upload.setOnClickListener(this);

    }
    private void filechooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/vnd.ms-powerpoint");//ppt
//        intent.setType("application/vnd.openxmlformats-officedocument.presentationml.presentation");//pptx
        startActivityForResult(intent.createChooser(intent,"請選擇一項要上傳的檔案"),PICKFILE_RESULT_CODE);
    }
    private void fileupload() {
        intent = new Intent();
        bundle = getIntent().getExtras();
        SharedPreferences sharedPreferences = getSharedPreferences("User" , MODE_PRIVATE);
        String User = sharedPreferences.getString("Name","");
//        Log.e("DEBUG","沒進來"+filepath.toString());
//        Log.e("DEBUG","     "+NameText.getText().toString().matches(""));
        if(NameText.getText().toString().matches("") != isEmpty){
            isEmpty=false;
        }
        if (filepath != null && !isEmpty ) {
            Log.e("DEBUG","有進來");
            Log.e("DEBUG","     "+NameText.getText().toString().matches(""));
            Pattern pattern = Pattern.compile("[.]");
            String[] PathSplit = pattern.split(filepath.toString());
            int size = PathSplit.length;
//            String TypeName = PathSplit[size-1];
            String TypeName = ".ppt";
            Log.e("DEBUG","filepath     "+filepath);
//            Log.e("DEBUG","Type "+TypeName);
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("上傳中......");
            progressDialog.show();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference();//屬性命名為message
            choosePath = new String[3];
            if (Share.isChecked() == true) {
                choosePath[0]="Teach";
                choosePath[1]="Share";
                choosePath[2]=NameText.getText().toString();
                Filename = "Public/";
            } else {
                Filename = "Private/";
            }
            myRef.child(choosePath[0]).child(choosePath[1]).child(User).child(choosePath[2]).setValue(TypeName);
            StorageReference riversRef = StorageRef.getStorage().getReference().child(Filename).child(User).child(NameText.getText().toString());
            Log.e("DEBUG","Line 84 riversRef "+riversRef);
            riversRef.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "上傳成功", Toast.LENGTH_SHORT).show();
                            intent.setClass(StorageActivity.this, menu_choose.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            // ...
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("已上傳" + ((int) progress) + "%，請稍後!");
                        }
                    });
        } else {
            Toast.makeText(this, "未選擇檔案 或 檔名不可為空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== PICKFILE_RESULT_CODE && resultCode==RESULT_OK && data!=null && data.getData()!=null){
            filepath=data.getData();

//            try {
//                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
//                image.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v==choose) {
            filechooser();
        }else {
             fileupload();
        }
    }
}