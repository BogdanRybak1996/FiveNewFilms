package com.bogdanrybak1996.fivenewfilms;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.*;

/**
 * Created by bohdan on 05.04.16.
 */
public class Parser {
    private ArrayList<Film> films = new ArrayList<Film>();
    private String htmlCode;
    public Parser(){
        RequestTask htm = new RequestTask();
        htm.execute("http://www.kinofilms.ua/ukr/afisha/city/17/");
        try {
            htmlCode = htm.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    private ArrayList<String> parseBlock(String htmlCode){
        ArrayList<String> bloks = new ArrayList<String>();
        Matcher m = Pattern.compile("<div class=\"cblock\" id=\"[a-z0-9]*\" data-offset=\"[0-9]*\">\\s*"+
                "<div class=\"row\">\\s*"+
                "<div class=\"span1 spanimg\" >\\s*"+
                "<a href=\".*?\"><img src=\".*?\" alt=\".*?\"></a>\\s*<a class=\".*?\" href=\".*?\"></a>\\s*"+
                "</div>\\s*"+
                "<div class=\"span6\">\\s*"+
                "<div class=\"h3\">\\s*"+
                "<a href=\".*?\" data-id=\".*?\">.*?</a>.*?\\s*"+
                "<br>.*?</div>\\s*"+
                "<div class=\"muted hide_short\"><tt><i class=\"icon-time\"></i>.*?</tt>\\s*"+
                "&ndash;.*?&ndash;.*?</div>\\s*"+
                "<p class=\"hide_short\">\\s*"+
                "<b>Режисери:</b>.*?<b>Актори:</b>.*?</p>\\s*"+
                "<div>\\s*"+
                "<a.*?</a>\\s*"+
                "<a.*?</a>\\s*"+
                "</div>\\s*"+
                "</div>\\s*"+
                "</div>\\s*").matcher(htmlCode);
        for(int i=0;i<5;i++){
            m.find();
            bloks.add(m.group());
        }
        return bloks;
    }
    class RequestTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... url) {
            StringBuilder sb = new StringBuilder();
            try {
                URL pageURL = new URL(url[0]);
                String inputLine;
                URLConnection uc = pageURL.openConnection();
                BufferedReader buff = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                while ((inputLine = buff.readLine()) != null) {
                    sb.append(inputLine);
                }
            }
            catch (Exception e) {
            }
            return sb.toString();
        }
    }
}

