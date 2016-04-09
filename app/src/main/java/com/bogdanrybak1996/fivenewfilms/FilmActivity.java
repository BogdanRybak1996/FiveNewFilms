package com.bogdanrybak1996.fivenewfilms;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;

public class FilmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        setInformation();
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

        //Перехід за посиланням
        TextView linkTextView = (TextView) findViewById(R.id.film_activity_link_id);
        linkTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse(film.getLink()));
                startActivity(webIntent);
            }
        });
        //Постер
        ImageView imageViewPoster = (ImageView) findViewById(R.id.film_activity_image_view_poster);
        new DownloadImageTask(imageViewPoster)
                .execute(film.getLinkPicture());
    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
