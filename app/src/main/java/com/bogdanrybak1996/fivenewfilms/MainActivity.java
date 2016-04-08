package com.bogdanrybak1996.fivenewfilms;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parser parser = null;
        ArrayList<Film> films = null;
        try {
            parser = new Parser();
            if(films==null) {                                       // Для того, щоб при зміні положення екрану не загружати дані заново
            films = parser.getFilms();
            }
            setStartContent(films);
        }
        catch (Exception e){

        }
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
        enableButtons();
        TextView handleText = (TextView) findViewById(R.id.text_handle);
        handleText.setText("Сьогодні в кінотеатрах");
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
    private void enableButtons(){                                                           // Відображаємо кнопки, якщо не виникнуло помилок з отриманням даних
        Button btn1 = (Button) findViewById(R.id.first_film_button_description);
        btn1.setVisibility(View.VISIBLE);
        Button btn2 = (Button) findViewById(R.id.second_film_button_description);
        btn2.setVisibility(View.VISIBLE);
        Button btn3 = (Button) findViewById(R.id.third_film_button_description);
        btn3.setVisibility(View.VISIBLE);
        Button btn4 = (Button) findViewById(R.id.fourth_film_button_description);
        btn4.setVisibility(View.VISIBLE);
        Button btn5 = (Button) findViewById(R.id.fifth_film_button_description);
        btn2.setVisibility(View.VISIBLE);

    }
}
