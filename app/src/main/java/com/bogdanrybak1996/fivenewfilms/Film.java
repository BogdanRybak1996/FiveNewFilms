package com.bogdanrybak1996.fivenewfilms;

import java.util.ArrayList;

/**
 * Created by bohdan on 05.04.16.
 * Клас містить інформацію про конкретний фільм.
 * Методи доступу дозволяють зберігати тут інформацію та отримувати її
 */
public class Film {
    private String name;
    private String year;
    private String genre;
    private String country;
    private String link;
    private String linkPicture;
    private ArrayList<String> directors = new ArrayList<String>();
    ArrayList<String> actors = new ArrayList<String>();
    public void setName(String name){
        this.name = name;
    }
    public void setYear(String year){
        this.year = year;
    }
    public void setGenre(String genre){
        this.genre = genre;
    }
    public void setCountry(String country){
        this.country = country;
    }
    public void setDirectors(ArrayList<String> directors){
        this.directors = directors;
    }
    public void setActors(ArrayList<String> actors){
        this.actors = actors;
    }
    public void setLink(String link){
        this.link = link;
    }
    public  void setLinkPicture(String linkPicture){
        this.linkPicture = linkPicture;
    }
    public String getName(){
        return name;
    }
    public String getYear(){
        return year;
    }
    public String getGenre(){
        return genre;
    }
    public String getCountry(){
        return country;
    }
    public String getLink(){
        return link;
    }
    public String getLinkPicture(){
        return linkPicture;
    }
    public String getDirectors(){
        String directors = "";
        for(int i=0;i<this.directors.size();i++){
            directors += this.directors.get(i);
            if(i!=this.directors.size()-1){
                directors+=", ";
            }
        }
        return directors;
    }
    public String getActors(){
        String actors = "";
        for(int i=0;i<this.actors.size();i++){
            actors += this.actors.get(i);
            if(i!=this.actors.size()-1){
                actors+=", ";
            }
        }
        return actors;
    }
}
