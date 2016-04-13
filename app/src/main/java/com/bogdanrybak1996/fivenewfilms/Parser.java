package com.bogdanrybak1996.fivenewfilms;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

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

    public Parser() {
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
        films = parseFilms(bloks);
    }

    public ArrayList<Film> getFilms() {
        return films;
    }

    // Метод виділяє з усього html коду лише ті частини, які відносяться до опису фільмів
    private ArrayList<String> parseBlock(String htmlCode) {
        ArrayList<String> bloks = new ArrayList<String>();
        Matcher m = Pattern.compile("(<div class=\"cblock\" id=\"[a-z0-9]*\" data-offset=\"[0-9]*\">\\s*" +
                "<div class=\"row\">\\s*" +
                "<div class=\"span1 spanimg\" >\\s*" +
                "<a href=\"[\\s\\S]*?\"><img src=\"[\\s\\S]*?\" alt=\"[\\s\\S]*?\"></a>\\s*<a class=\"[\\s\\S]*?\" href=\"[\\s\\S]*?\"></a>\\s*" +
                "</div>\\s*" +
                "<div class=\"span6\">\\s*" +
                "<div class=\"h3\">\\s*" +
                "<a href=\"[\\s\\S]*?\" data-id=\"[\\s\\S]*?\">[\\s\\S]*?</a>[\\s\\S]*?\\s*" +
                "<br>[\\s\\S]*?</div>\\s*" +
                "<div class=\"muted hide_short\"><tt><i class=\"icon-time\"></i>[\\s\\S]*?</tt>\\s*" +
                "&ndash;[\\s\\S]*?&ndash;[\\s\\S]*?</div>\\s*" +
                "<p class=\"hide_short\">\\s*" +
                "<b>Режисери:</b>[\\s\\S]*?<b>Актори:</b>[\\s\\S]*?</p>\\s*" +
                "<div>\\s*" +
                "<a[\\s\\S]*?</a>\\s*?" +
                "<a[\\s\\S]*?</a>\\s*?" +
                "</div>\\s*?" +
                "</div>\\s*?" +
                "</div>)\\s*?<div class=\"afisha").matcher(htmlCode);

        for (int i = 0; i < 5; i++) {
            m.find();
            bloks.add(m.group(1));
        }
        return bloks;
    }

    //Метод виділяє із блоків, знайдених раніше, інформацію про фільм
    private ArrayList<Film> parseFilms(ArrayList<String> bloks) {
        ArrayList<Film> films = new ArrayList<>();
        Matcher matcher;
        for (int i = 0; i < bloks.size(); i++) {
            films.add(new Film());
            //назва фільму
            matcher = Pattern.compile("<a href=\".*?\"><img src=\".*?\" alt=\"(.*?)\"></a>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setName(matcher.group(1));

            //Рік прем’єри
            matcher = Pattern.compile("<a href=\".*?\" data-id=\".*?\">.*?</a> \\((.*?)\\)").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setYear(matcher.group(1));

            //Жанри фільму
            matcher = Pattern.compile("&ndash; (.*?) &ndash;.*?</div>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setGenre(matcher.group(1));

            //Країна виробництва
            matcher = Pattern.compile("&ndash; .*? &ndash; (.*?)</div>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setCountry(matcher.group(1));

            //Посилання
            matcher = Pattern.compile("<a href=\"(.*?)\"><img src=\".*?\" alt=\".*?\"></a>\\s*<a class=\"poster_play\" href=\".*?\"></a>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setLink("http://www.kinofilms.ua" + matcher.group(1));

            //Посилання на зображення
            matcher = Pattern.compile("<a href=\".*?\"><img src=\"(.*?)\" alt=\".*?\"></a>\\s*<a class=\"poster_play\" href=\".*?\"></a>").matcher(bloks.get(i));
            matcher.find();
            films.get(i).setLinkPicture("http://www.kinofilms.ua" + matcher.group(1));

            // Опис фільму (переходимо за посиланням та беремо опис звідти)
            String filmLink = films.get(i).getLink();
            RequestTask filmHTMRequest = new RequestTask();
            filmHTMRequest.execute(filmLink);
            String filmHTML = null;
            try {
                filmHTML = filmHTMRequest.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            matcher = Pattern.compile("<div itemprop=\"description\"><p>(.*?)</p><p></p></div>").matcher(filmHTML);
            matcher.find();
            String Description = matcher.group(1);

            //замінюємо спеціальні символи в описі
            Description = Description.replaceAll("&nbsp;", " ");
            Description = Description.replaceAll("&hellip;", "...");
            Description = Description.replaceAll("&prime;", "\'");
            Description = Description.replaceAll("&ndash;", "-");
            Description = Description.replaceAll("&mdash;", "-");
            Description = Description.replaceAll("&laquo;", "\"");
            Description = Description.replaceAll("&raquo;", "\"");
            Description = Description.replaceAll("&lsquo;", "\'");
            Description = Description.replaceAll("&rsquo;", "\'");
            Description = Description.replaceAll("&#39;", "\'");
            Description = Description.replaceAll("</p>", "\n");
            Description = Description.replaceAll("<p>", "    ");
            films.get(i).setDescription(Description);

            // Режисери
            matcher = Pattern.compile("<b>Режисери:</b>.*?<b>Актори:</b>").matcher(bloks.get(i));
            matcher.find();
            String directorStr = matcher.group();
            matcher = Pattern.compile("<a href=\".*?\" rel=\"nofollow\">(.*?)</a>").matcher(directorStr);
            ArrayList<String> directors = new ArrayList<String>();
            while (matcher.find()) {
                directors.add(matcher.group(1));
            }
            films.get(i).setDirectors(directors);

            // Актори
            matcher = Pattern.compile("<b>Актори:</b>.*?</p>").matcher(bloks.get(i));
            matcher.find();
            String actorsStr = matcher.group();
            matcher = Pattern.compile("<a href=\".*?\" rel=\"nofollow\">(.*?)</a>").matcher(actorsStr);
            ArrayList<String> actors = new ArrayList<String>();
            while (matcher.find()) {
                actors.add(matcher.group(1));
            }
            films.get(i).setActors(actors);

            //Прем’єра в світі
            matcher = Pattern.compile("<img src=\"/images/primworld.jpg\">&nbsp;<a href='.*?'>(.*?)&nbsp;(.*?)&nbsp;(.*?)</a>").matcher(filmHTML);
            if(matcher.find()) {
                films.get(i).setPremierWorld(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
            }

            //Прем’єра в Україні
            matcher = Pattern.compile("<img src=\"/images/primukraine.jpg\">&nbsp;<a href='.*?'>(.*?)&nbsp;(.*?)&nbsp;(.*?)</a>").matcher(filmHTML);
            if(matcher.find()) {
                films.get(i).setPremierUkraine(matcher.group(1) + " " + matcher.group(2) + " " + matcher.group(3));
            }
            // Рейтинг
            matcher = Pattern.compile("Рейтинг: <span id=\"rate_value\">(.*?)</span>").matcher(filmHTML);
            matcher.find();
            films.get(i).setRating(matcher.group(1));
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
            } catch (Exception e) {
            }
            return sb.toString();
        }
    }
}

