package com.bogdanrybak1996.fivenewfilms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URI;

public class FilmActivity extends AppCompatActivity {

    private static final String KEY_VIDEO_TIME = "VIDEO_TIME";
    private static final String KEY_VIDEO_STATUS = "VIDEO_STATUS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        setInformation();
        VideoView vv = (VideoView) findViewById(R.id.trailer_video);
        if(savedInstanceState != null){
            vv.seekTo(savedInstanceState.getInt(KEY_VIDEO_TIME));
            boolean st = savedInstanceState.getBoolean(KEY_VIDEO_STATUS);
            if(st){
                vv.start();
            }
            else{
                vv.pause();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        VideoView vv = (VideoView) findViewById(R.id.trailer_video);
        outState.putInt(KEY_VIDEO_TIME,vv.getCurrentPosition());
        if(vv.isPlaying()){
            outState.putBoolean(KEY_VIDEO_STATUS,true);
        }
        else{
            outState.putBoolean(KEY_VIDEO_STATUS,false);
        }
    }

    private void setInformation(){
        final Film film = getIntent().getParcelableExtra("film");
        //Назва фільму
        TextView textViewName = (TextView) findViewById(R.id.film_activity_text_view_name);
        textViewName.setText(film.getName());

        //Жанр фільму
        TextView textViewGenre = (TextView) findViewById(R.id.film_activity_text_view_genre);
        textViewGenre.setText("Жанр: " + film.getGenre());

        //Країна
        TextView textViewCountry = (TextView) findViewById(R.id.fim_activity_text_view_country);
        textViewCountry.setText("Країна: " + film.getCountry());

        //Рік
        TextView textViewYear = (TextView) findViewById(R.id.film_activity_text_view_year);
        textViewYear.setText("Рік: " + film.getYear());

        //Режисери
        TextView textViewDirectors = (TextView) findViewById(R.id.film_activity_text_view_directors);
        textViewDirectors.setText("Режисери: " + film.getDirectors());

        //Актори
        TextView textViewActors = (TextView) findViewById(R.id.film_activity_text_view_actors);
        textViewActors.setText("Актори: " + film.getActors());

        //Опис
        TextView textViewDescription = (TextView) findViewById(R.id.description_text_view);
        textViewDescription.setText(film.getDescription());

        //Прем’єра в світі
        TextView textViewWorldPremier = (TextView) findViewById(R.id.premier_world);
        if(film.getPremierWorld()!=null) {
            textViewWorldPremier.setText("Світ: " + film.getPremierWorld());
        }

        //Прем’єра в Україні
        TextView textViewUkrainePremier = (TextView) findViewById(R.id.premier_ukraine);
        if(film.getPremierUkraine()!=null) {
            textViewUkrainePremier.setText("Україна: " + film.getPremierUkraine());
        }

        //Рейтинг
        TextView ratingTextView = (TextView) findViewById(R.id.rating_text_view);
        ratingTextView.setText(film.getRating());


        //Перехід за посиланням
        TextView linkTextView = (TextView) findViewById(R.id.film_activity_link);
        //Постер
        ImageView imageViewPoster = (ImageView) findViewById(R.id.film_activity_image_view_poster);

        Picasso.with(this).load(film.getLinkPicture()).into(imageViewPoster);

        //Трейлер
        Uri uri = Uri.parse(film.getTrailerURL());
        VideoView trailerVideo = (VideoView) findViewById(R.id.trailer_video);
        MediaController mc = new MediaController(this);
        trailerVideo.setMediaController(mc);
        trailerVideo.requestFocus(0);
        trailerVideo.setVideoURI(uri);
        trailerVideo.setZOrderOnTop(true);
        Button btn = (Button) findViewById(R.id.video_intent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(film.getTrailerURL()),"video/*");
                startActivity(intent);
            }
        });

    }
}

