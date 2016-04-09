package com.bogdanrybak1996.fivenewfilms;

import java.util.ArrayList;

/**
 * Created by bohdan on 09.04.16.
 */
public class Sessions {
    private String theatre;
    private ArrayList<String> timesAndPrices;

    public void setTheatre(String theatre){
        this.theatre = theatre;
    }
    public void setTimesAndPrices(ArrayList<String> timesAndPrices){
        this.timesAndPrices = timesAndPrices;
    }
    public String getInfo(){
        String info = theatre+ ":" + "\n";
        for(int i=0;i<timesAndPrices.size();i++){
            info+=timesAndPrices.get(i) + ", ";
        }
        return info;
    }
}
