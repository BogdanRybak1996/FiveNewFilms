package com.bogdanrybak1996.fivenewfilms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parser parser = new Parser();
        ArrayList<Film> films = null;
        if(films==null) {
            films = parser.getFilms();
        }
        setStartContent(films);

    }

    /**
     * Метод заповнює початковий екран інформацією
     * @param films
     */
    private void setStartContent(ArrayList<Film> films){
        TextView nameTextView = null;
        TextView genreTextView = null;
        TextView countryTextView = null;
        TextView yearTextView = null;
        TextView directorsTextView = null;
        ImageView posterImageView = null;

        for(int i=0;i<5;i++){
            switch (i){
                case 0: nameTextView = (TextView) findViewById(R.id.first_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.first_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.first_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.first_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.first_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.first_film_image_view_poster);
                    break;
                case 1: nameTextView = (TextView) findViewById(R.id.second_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.second_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.second_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.second_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.second_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.second_film_image_view_poster);
                    break;
                case 2: nameTextView = (TextView) findViewById(R.id.third_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.third_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.third_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.third_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.third_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.third_film_image_view_poster);
                    break;
                case 3: nameTextView = (TextView) findViewById(R.id.fourth_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.fourth_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.fourth_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.fourth_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.fourth_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.fourth_film_image_view_poster);
                    break;
                case 4: nameTextView = (TextView) findViewById(R.id.fifth_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.fifth_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.fifth_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.fifth_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.fifth_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.fifth_film_image_view_poster);
                    break;
            }
            nameTextView.setText(films.get(i).getName());
            genreTextView.setText("Жанр: " + films.get(i).getGenre());
            countryTextView.setText("Країна: " + films.get(i).getCountry());
            yearTextView.setText("Рік: " + films.get(i).getYear());
            directorsTextView.setText("Режисери: " + films.get(i).getDirectors());
            new DownloadImageTask(posterImageView)
                    .execute(films.get(i).getLinkPicture());

        }
    }

    /**
     * Для завантаження картинки з інтернету
     */
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
