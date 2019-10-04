package com.pickle.ourgames.ui;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.pickle.ourgames.R;
import com.pickle.ourgames.model.Games;
import com.pickle.ourgames.ui.viewmodels.DetailViewModel;
import com.squareup.picasso.Picasso;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;

public class ReviewsFragment extends Fragment {
    RatingBar ratingBar;
    ImageView saveReviewIcon;
    String uid;
    int gameId = 0;
    EditText etReview;
    ImageView imgCover;
    TextView tvYear;
    TextView tvTitle;
   DetailViewModel viewModel;
    public static ReviewsFragment newInstance() {
        return new ReviewsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.reviews_fragment, container, false);
            tvTitle = v.findViewById(R.id.txt_title_add_review);
        tvYear = v.findViewById(R.id.txt_release_year_add_review);
        imgCover = v.findViewById(R.id.img_cover_add_review);
        etReview =  v.findViewById(R.id.edit_text_add_review);
        ratingBar = v.findViewById(R.id.rating_bar_add_review);
        saveReviewIcon = v.findViewById(R.id.img_checkmark_add_review);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  mViewModel = ViewModelProviders.of(this).get(ReviewsViewModel.class);
      uid =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        viewModel = ViewModelProviders.of(this.getActivity()).get(DetailViewModel.class);
        Log.i("Viewmodel review", viewModel.toString());

        viewModel.getGameIdLiveData().observe(this, item ->{
           Games game = item.get(0);
          String title = game.getName();
          long year = game.getFirstReleaseDate();
            gameId =  game.getId();
            int date = Instant.ofEpochSecond(year).atZone(ZoneId.systemDefault()).getYear();
            Log.i("YEAR", String.valueOf(date));
          tvTitle.setText(title);
           tvYear.setText(String.valueOf(date));
        });
        viewModel.getDetailContainer().observe(this, detailItems -> {
            String cover = detailItems.getCoversList().get(0).getUrl();

            Picasso.get().load("http:"+ cover).fit().into(imgCover);

        });

        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser){
                ratingBar.setRating(rating);


            }
        });
        saveReviewIcon.setOnClickListener(click ->{
            //add or update rating
            if((gameId != 0) && (uid != null)) {
                viewModel.addStarredGame(ratingBar.getRating(),gameId, uid );
                viewModel.addReview(gameId,uid,etReview.getText().toString());
            }
        });
    }

}
