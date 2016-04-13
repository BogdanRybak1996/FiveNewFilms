package com.bogdanrybak1996.fivenewfilms;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by bohdan on 05.04.16.
 * Клас містить інформацію про конкретний фільм.
 * Методи доступу дозволяють зберігати тут інформацію та отримувати її
 */
public class Film implements Parcelable {
    private String name;
    private String year;
    private String genre;
    private String country;
    private String link;
    private String linkPicture;
    private String description;
    private ArrayList<String> directors = new ArrayList<String>();
    private ArrayList<String> actors = new ArrayList<String>();
    private String premierUkraine;
    private String premierWorld;
    private String rating;
    public Film(){

    }

    protected Film(Parcel in) {                     // Для передачі об’єкту в інший activity через Intents
        name = in.readString();
        year = in.readString();
        genre = in.readString();
        country = in.readString();
        link = in.readString();
        linkPicture = in.readString();
        description = in.readString();
        directors = in.createStringArrayList();
        actors = in.createStringArrayList();
        premierUkraine = in.readString();
        premierWorld = in.readString();
        rating = in.readString();
    }

    public static final Creator<Film> CREATOR = new Creator<Film>() {
        @Override
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

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
    public void setPremierUkraine(String premierUkraine){
        this.premierUkraine = premierUkraine;
    }
    public void setPremierWorld(String premierWorld){
        this.premierWorld = premierWorld;
    }
    public  void setLinkPicture(String linkPicture){
        this.linkPicture = linkPicture;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setRating(String rating){
        this.rating = rating;
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
    public String getPremierUkraine(){
        return premierUkraine;
    }
    public  String getPremierWorld(){
        return premierWorld;
    }
    public String getLinkPicture(){
        return linkPicture;
    }
    public String getDescription(){
        return description;
    }
    public String getRating(){
        return rating;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {                 // Для передачі об’єкту в інший activity через Intents
        dest.writeString(name);
        dest.writeString(year);
        dest.writeString(genre);
        dest.writeString(country);
        dest.writeString(link);
        dest.writeString(linkPicture);
        dest.writeString(description);
        dest.writeStringList(directors);
        dest.writeStringList(actors);
        dest.writeString(premierUkraine);
        dest.writeString(premierWorld);
        dest.writeString(rating);
    }
}
