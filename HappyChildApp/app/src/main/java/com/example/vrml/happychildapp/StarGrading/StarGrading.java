package com.example.vrml.happychildapp.StarGrading;

/**
 * Created by Nora on 2017/11/7.
 */

public class StarGrading {
    public static int getStar(String unit, int total, int correct) {
        double i = correct / (total * 1.0);
        int star = 0;
        if (i <= 0.3) {
            star = 1;
        } else if (i <= 0.7) {
            star = 2;
        } else {
            star = 3;
        }
        return star;
    }
}