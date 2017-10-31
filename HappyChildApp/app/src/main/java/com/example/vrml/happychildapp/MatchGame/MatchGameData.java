package com.example.vrml.happychildapp.MatchGame;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by Nora on 2017/10/27.
 */

public class MatchGameData {
    DataSnapshot dataSnapshot;
    String[] Chinese,Phonetic,ImagePath;
    String[] temp2 = null;
    String[] newChinese,newPhonetic,newImagePath;
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
        Chinese = new String[count];
        Phonetic = new String[count];
        ImagePath = new String[count];
        for (DataSnapshot temp : dataSnapshot.getChildren()) {

            temp2 = ((String) temp.getValue()).split(";");
            this.Chinese[i] = temp2[0];
            this.Phonetic[i] = temp2[1].replaceAll(","," ");
            this.ImagePath[i] = temp2[2];
            i++;
        }
    }
    public int compare(String string,String imagePath,String MODE){
        String[] check=MODE.equals("Chinese")?Chinese:Phonetic;
        for (int i=0;i<this.ImagePath.length;i++){
            if (this.ImagePath[i].equals(imagePath)){
                Log.e("DEBUG","Data44"+imagePath+"  String:"+string);
                if (string.equals(check[i])){
                    return -1;
                }
                else {
                    string = check[i];

                    Log.e("DEBUG","Data string"+string);
                    for (int j=0;j<this.Chinese.length;j++){
                        if(MODE.equals("Chinese")){
                            if( this.newChinese[j].equals(string))
                                return j;
                        }else if(MODE.equals("Phonetic")){
                            Log.e("DEBUG","56 "+newPhonetic[j]+"  "+string);
                            if( this.newPhonetic[j].equals(string))
                                return j;
                        }
                    }
                }
            }
        }
        return -1;
    }
    public String[] getChineseData(){return this.Chinese;}
    public String[] getPhoneticData(){return this.Phonetic;}
    public String[] getImagePathData(){return this.ImagePath;}

    public String[] getNewChineseData(){return this.newChinese;}
    public String[] getNewPhoneticData(){return this.newPhonetic;}
    public String[] getNewImagePathData(){return this.newImagePath;}

    public void setChineseData(String[] ChineseData){this.newChinese=ChineseData;}
    public void setPhoneticData(String[] PhoneticData){this.newPhonetic=PhoneticData;}
    public void setImagePathData(String[] ImagePathData){this.newImagePath=ImagePathData;}
}
