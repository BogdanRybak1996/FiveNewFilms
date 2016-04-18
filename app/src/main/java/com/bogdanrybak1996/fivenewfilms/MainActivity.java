package com.bogdanrybak1996.fivenewfilms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Film> films = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Parser parser = null;
        try {
            parser = new Parser();
            if(films==null) {                                       // Для того, щоб при зміні положення екрану не загружати дані заново
            films = parser.getFilms();
            }
            setStartContent(films);
        }
        catch (Exception e){

        }
        //Додаємо обробники подій до кнопок
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.first_film_button_description:
                        Intent intent = new Intent(getApplicationContext(),FilmActivity.class);
                        intent.putExtra("film",films.get(0));
                        startActivity(intent);
                        break;
                    case R.id.second_film_button_description:
                        intent = new Intent(getApplicationContext(),FilmActivity.class);
                        intent.putExtra("film",films.get(1));
                        startActivity(intent);
                        break;
                    case R.id.third_film_button_description:
                        intent = new Intent(getApplicationContext(),FilmActivity.class);
                        intent.putExtra("film",films.get(2));
                        startActivity(intent);
                        break;
                    case R.id.fourth_film_button_description:
                        intent = new Intent(getApplicationContext(),FilmActivity.class);
                        intent.putExtra("film",films.get(3));
                        startActivity(intent);
                        break;
                    case R.id.fifth_film_button_description:
                        intent = new Intent(getApplicationContext(),FilmActivity.class);
                        intent.putExtra("film",films.get(4));
                        startActivity(intent);
                        break;
                }
            }
        };
        Button buttonFirstFilmInfo = (Button) findViewById(R.id.first_film_button_description);
        buttonFirstFilmInfo.setOnClickListener(onClickListener);
        Button buttonSecondFilmInfo = (Button) findViewById(R.id.second_film_button_description);
        buttonSecondFilmInfo.setOnClickListener(onClickListener);
        Button buttonThirdFilmInfo = (Button) findViewById(R.id.third_film_button_description);
        buttonThirdFilmInfo.setOnClickListener(onClickListener);
        Button buttonFourthFilmInfo = (Button) findViewById(R.id.fourth_film_button_description);
        buttonFourthFilmInfo.setOnClickListener(onClickListener);
        Button buttonFifthFilmInfo = (Button) findViewById(R.id.fifth_film_button_description);
        buttonFifthFilmInfo.setOnClickListener(onClickListener);
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
        TextView handleText = (TextView) findViewById(R.id.text_handle);
        handleText.setText("Сьогодні в кінотеатрах");
        for(int i=0;i<films.size();i++){
            switch (i){
                case 0: nameTextView = (TextView) findViewById(R.id.first_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.first_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.first_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.first_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.first_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.first_film_image_view_poster);
                    Button btn1 = (Button) findViewById(R.id.first_film_button_description);
                    if(films.get(i).getName()!=null)
                        btn1.setVisibility(View.VISIBLE);
                    break;
                case 1: nameTextView = (TextView) findViewById(R.id.second_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.second_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.second_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.second_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.second_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.second_film_image_view_poster);
                    Button btn2 = (Button) findViewById(R.id.second_film_button_description);
                    if(films.get(i).getName()!=null)
                        btn2.setVisibility(View.VISIBLE);
                    break;
                case 2: nameTextView = (TextView) findViewById(R.id.third_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.third_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.third_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.third_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.third_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.third_film_image_view_poster);
                    Button btn3 = (Button) findViewById(R.id.third_film_button_description);
                    if(films.get(i).getName()!=null)
                        btn3.setVisibility(View.VISIBLE);

                    break;
                case 3: nameTextView = (TextView) findViewById(R.id.fourth_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.fourth_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.fourth_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.fourth_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.fourth_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.fourth_film_image_view_poster);
                    Button btn4 = (Button) findViewById(R.id.fourth_film_button_description);
                    if(films.get(i).getName()!=null)
                        btn4.setVisibility(View.VISIBLE);
                    break;
                case 4: nameTextView = (TextView) findViewById(R.id.fifth_film_text_view_name);
                    genreTextView = (TextView) findViewById(R.id.fifth_film_text_view_genre);
                    countryTextView = (TextView) findViewById(R.id.fifth_film_text_view_country);
                    yearTextView = (TextView) findViewById(R.id.fifth_film_text_view_year);
                    directorsTextView = (TextView) findViewById(R.id.fifth_film_text_view_directors);
                    posterImageView = (ImageView) findViewById(R.id.fifth_film_image_view_poster);
                    Button btn5 = (Button) findViewById(R.id.fifth_film_button_description);
                    if(films.get(i).getName()!=null)
                        btn5.setVisibility(View.VISIBLE);
                    break;
            }
            if(films.get(i).getName()!=null) {                          // Фільмів на сайті може бути менше 5
                nameTextView.setText(films.get(i).getName());
                genreTextView.setText("Жанр: " + films.get(i).getGenre());
                countryTextView.setText("Країна: " + films.get(i).getCountry());
                yearTextView.setText("Рік: " + films.get(i).getYear());
                directorsTextView.setText("Режисери: " + films.get(i).getDirectors());
                Picasso.with(this).load(films.get(i).getLinkPicture()).into(posterImageView);       // Завантажуємо картинку з інтернету за допомогою Picasso
            }

        }
    }
}
