package com.example.moviedb.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_text_code,lbl_text_title,lbl_text_genre,lbl_text_desc,lbl_text_duration;
    private String movie_id,title,duration,poster,desc ="";
    private ImageView img_details,backbut;
    private MovieViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");
        img_details = findViewById(R.id.img_path_details);
        title = intent.getStringExtra("title_movie");
        poster = intent.getStringExtra("poster_movie");
        desc = intent.getStringExtra("overview_movie");
        viewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showDetails);

        lbl_text_code = findViewById(R.id.lbl_movie_details);
        lbl_text_code.setText(movie_id);
        lbl_text_title=findViewById(R.id.lbl_movie_title);
        lbl_text_title.setText(title);
        lbl_text_genre=findViewById(R.id.lbl_movie_genre);
        lbl_text_duration=findViewById(R.id.lbl_duration_movie);
        Glide.with(MovieDetailsActivity.this).load(Const.IMG_URL+poster).into(img_details);
        lbl_text_desc=findViewById(R.id.lbl_desc_movie);
        lbl_text_desc.setText(desc);
        backbut=findViewById(R.id.backbut);
        backbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private Observer<Movies> showDetails = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
                String genre="";
                String duration = String.valueOf(movies.getRuntime());
                for (int i = 0; i<movies.getGenres().size(); i++) {
                    if (i == movies.getGenres().size() - 1) {
                        genre += movies.getGenres().get(i).getName();
                    }else{
                        genre += movies.getGenres().get(i).getName()+", ";
                    }
                }
                lbl_text_genre.setText(genre);
                lbl_text_duration.setText(duration);


        }
    };

    @Override
    public void onBackPressed() {
        finish();
    }
}