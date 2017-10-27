package com.example.vrml.happychildapp.MatchGame;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Nora on 2017/10/27.
 */

public class MatchGameData {
    DataSnapshot dataSnapshot;
    String[] chinese,phonetic,imagePath;
    String[] temp2 = null;
    public MatchGameData(){}
    public MatchGameData(Bundle bundle, DataSnapshot dataSnapshot){
        String Subject = bundle.getString("Subject");
        String Mode = bundle.getString("Mode");
        String Unit = bundle.getString("Unit");
        String Lesson = bundle.getString("Lesson");
        dataSnapshot = dataSnapshot.child(Subject).child(Mode).child(Unit).child(Lesson);
        this.dataSnapshot = dataSnapshot;
        int i = 0;
        int count = (int) dataSnapshot.getChildrenCount();
        chinese = new String[count];
        phonetic = new String[count];
        imagePath = new String[count];
        for (DataSnapshot temp : dataSnapshot.getChildren()) {

            temp2 = ((String) temp.getValue()).split(";");
            this.chinese[i] = temp2[0];
            this.phonetic[i] = temp2[1].replaceAll(","," ");
            this.imagePath[i] = temp2[2];
            i++;
        }
    }
    public boolean compare(String string,String imagePath){
        for (int i=0;i<chinese.length;i++){
            if (this.imagePath[i].equals(imagePath)){
                if (this.chinese[i].equals(string)||this.phonetic[i].equals(string))
                    return true;
                return false;
            }
        }return false;
    }
    public String[] getChineseData(){return this.chinese;}
    public String[] getPhoneticData(){return this.phonetic;}
    public String[] getImagePathData(){return this.imagePath;}
}
