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

