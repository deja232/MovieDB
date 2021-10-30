package com.example.moviedb.view.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.view.activities.MovieDetailsActivity;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MovieDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailsFragment newInstance(String param1, String param2) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView  title_text, genre_text, rating_text, description_text, popular_text, date_text, tagline;
    private String movie_id;
    private ImageView image_text,img_backdrop;
    private LinearLayout linear_product;
    private MovieViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        image_text = view.findViewById(R.id.img_fragment_details);
        title_text = view.findViewById(R.id.title_fragment);
        tagline = view.findViewById(R.id.tagline);
        img_backdrop = view.findViewById(R.id.img_backdrop);
        genre_text = view.findViewById(R.id.genre_fragment);
        date_text = view.findViewById(R.id.date_fragment);
        popular_text = view.findViewById(R.id.popularity);
        rating_text = view.findViewById(R.id.rating_fragment);
        linear_product = view.findViewById(R.id.linear_product);
        description_text = view.findViewById(R.id.description_fragment_details);

        movie_id = getArguments().getString("movieId");

        viewModel = new ViewModelProvider(getActivity()).get(MovieViewModel.class);
        viewModel.getMovieById(movie_id);
        viewModel.getResultGetMovieById().observe(getActivity(), showMoviesResults);

        return view;
    }

    private Observer<Movies> showMoviesResults = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String genre = "";
            String orang = String.valueOf(movies.getVote_count());
            String backdrop = Const.IMG_URL+movies.getBackdrop_path();
            String tag = movies.getTagline();
            String title = movies.getTitle();
            String rating = String.valueOf(movies.getVote_average()) + " / 10" + "\n"+ "Vote : "+"( " + orang + " )";
            String date = movies.getRelease_date();
            String popular ="Views : "+ String.valueOf(movies.getPopularity());
            String description = movies.getOverview();
            for (int i = 0; i < movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() -1)
                {
                    genre += movies.getGenres().get(i).getName();
                }else{
                    genre += movies.getGenres().get(i).getName()+", ";
                }
            }
            Glide.with(getActivity()).load(Const.IMG_URL+movies.getPoster_path()).into(image_text);
            Glide.with(getActivity()).load(backdrop).into(img_backdrop);
            title_text.setText(title);
            description_text.setText(description);
            genre_text.setText(genre);
            tagline.setText(tag);
            rating_text.setText(rating);
            popular_text.setText(popular);
            date_text.setText(date);

            for (int i = 0; i < movies.getProduction_companies().size(); i++){
                ImageView image = new ImageView(linear_product.getContext());
                String logo = Const.IMG_URL + movies.getProduction_companies()
                        .get(i)
                        .getLogo_path();
                String namecomp = movies.getProduction_companies()
                        .get(i)
                        .getName();
                if(movies.getProduction_companies()
                    .get(i)
                    .getLogo_path() == null){
                    image.setImageDrawable(getResources().getDrawable(R.drawable.yiren, getActivity().getTheme()));
                }else{
                    Glide.with(getActivity()).load(logo).into(image);
                }
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(250, 250);
                image.setBackgroundColor(Color.parseColor("#A9A9A9"));
                lp.setMargins(30,0,30,0);
                image.setPadding(20,20,20,20);
                image.setLayoutParams(lp);
                linear_product.addView(image);
                image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar bar = Snackbar.make(view, namecomp, Snackbar.LENGTH_LONG);
                        bar.setAnchorView(R.id.bottom_nav_main_menu);
                        bar.show();
                    }
                });
            }
        }
    };
}