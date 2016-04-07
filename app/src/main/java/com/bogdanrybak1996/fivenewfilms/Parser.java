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
        ArrayList<String> bloks = parseBlock(htmlCode);
    }

    // Метод виділяє з усього html коду лише ті частини, які відносяться до опису фільмів
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
    //Метод виділяє із блоків, знайдених раніше, інформацію про фільм
    private ArrayList<Film> parseFilms(ArrayList<String> bloks) {
        ArrayList<Film> films = new ArrayList<>();
        Matcher matcher;
        for (int i = 0; i < bloks.size(); i++) {
            //назва фільму
            matcher = Pattern.compile("<a href=\".*?\"><img src=\"/*?\" alt=\"(.*?)\"></a>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setName(matcher.group(1));

            //Рік прем’єри
            matcher = Pattern.compile("<a href=\".*?\" data-id=\".*?\">.*?</a> (\\(.*?\\))").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setYear(matcher.group(1));

            //Жанри фільму
            matcher = Pattern.compile("&ndash; (.*?) &ndash;.*?</div>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setGenre(matcher.group(1));

            //Країна виробництва
            matcher = Pattern.compile("&ndash; .*? &ndash;(.*?)</div>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setCountry(matcher.group(1));

            //Посилання
            matcher = Pattern.compile("<a href=\"(.*?)\"><img src=\".*?\" alt=\".*?\"></a>\\s*<a class=\"poster_play\" href=\".*?\"></a>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setLink("http://www.kinofilms.ua" + matcher.group(1));

            //Посилання на зображення
            matcher = Pattern.compile("<a href=\".*?\"><img src=\"(.*?)\" alt=\".*?\"></a>\\s*<a class=\"poster_play\" href=\".*?\"></a>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setLink("http://www.kinofilms.ua" + matcher.group(1));

            // Опис фільму (переходимо за посиланням та беремо опис звідти)
            String filmHTML = films.get(i).getLink();
            RequestTask filmHTMRequest = new RequestTask();
            filmHTMRequest.execute(filmHTML);
            matcher=Pattern.compile("<div itemprop=\"description\"><p>(.*?)</p><p></p></div>").matcher(filmHTML);

            // Режисери
            matcher = Pattern.compile("<b>Режисери:</b>.*?<b>Актори:</b>").matcher(bloks.get(i));
            matcher.find();
            String directorStr = matcher.group();
            matcher = Pattern.compile("<a href=\".*?\" rel=\"nofollow\">(.*?)</a>").matcher(directorStr);
            ArrayList<String> directors = new ArrayList<String>();
            while (matcher.find()){
                directors.add(matcher.group(1));
            }
            films.get(i).setDirectors(directors);

            // Актори
            matcher = Pattern.compile("<b>Актори:</b>.*?</p>").matcher(bloks.get(i));
            matcher.find();
            String actorsStr = matcher.group();
            matcher = Pattern.compile("<a href=\".*?\" rel=\"nofollow\">(.*?)</a>").matcher(actorsStr);
            ArrayList<String> actors = new ArrayList<String>();
            while (matcher.find()){
                actors.add(matcher.group(1));
            }
            films.get(i).setActors(actors);
        }
        return films;
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

