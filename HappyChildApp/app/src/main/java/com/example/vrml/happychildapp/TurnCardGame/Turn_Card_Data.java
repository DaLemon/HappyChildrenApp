package com.example.vrml.happychildapp.TurnCardGame;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by VRML on 2017/5/21.
 */

public class Turn_Card_Data {
    //List<String> array = new ArrayList<String>();
    String[] array = new String[16];
    DataSnapshot dataSnapshot;
    private int size;
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
        String[] temp = temp2.split("");
        size=temp.length/2;
        for (int i=0 ; i<array.length;i++) {
            array[i]  ="";
        }
        for (int i=1;i<temp.length;i++) {
            array[i-1] = temp[i];
        }

    }

    public Turn_Card_Data(){
        this.array = new String[]{"1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8"};
        //this.array = Arrays.asList("1", "1", "2", "2", "3", "3", "4", "4", "5", "5", "6", "6", "7", "7", "8", "8");
    }

    public String[] getData(){
        return this.array;
    }
    public int getSize(){return size;}
    public DataSnapshot getDataSnapshot() {return this.dataSnapshot;}
}
