package com.example.vrml.happychildapp.TurnCardGame;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;

import java.util.Arrays;

/**
 * Created by VRML on 2017/5/21.
 */

public class Turn_Card_Data {
    //List<String> array = new ArrayList<String>();
    String[] array;
    DataSnapshot dataSnapshot;
    public Turn_Card_Data get() {
        return new Turn_Card_Data();
    }
    public Turn_Card_Data(String[] array){
       this.array =array.clone();

    }
    public Turn_Card_Data (Bundle bundle, DataSnapshot dataSnapshot){
        String Subject = bundle.getString("Subject");
        String Mode = bundle.getString("Mode");
        String Unit = bundle.getString("Unit");
        String Lesson = bundle.getString("Lesson");
        dataSnapshot = dataSnapshot.child(Subject).child(Mode).child(Unit).child(Lesson);
        this.dataSnapshot = dataSnapshot;
        String temp2 = null;

        for (DataSnapshot temp : dataSnapshot.getChildren()) {

            temp2 = (String) temp.getValue();

        }
        array = Arrays.copyOfRange(temp2.split(""),1,temp2.split("").length);


    }

    public Turn_Card_Data(){
        this.array = new String[]{"1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8"};
        //this.array = Arrays.asList("1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8");
    }

    public String[] getData(){
        return this.array;
    }
    public DataSnapshot getDataSnapshot() {return this.dataSnapshot;}
}
