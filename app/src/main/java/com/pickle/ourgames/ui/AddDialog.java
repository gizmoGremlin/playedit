package com.pickle.ourgames.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.pickle.ourgames.R;
import com.pickle.ourgames.storage.SharedPref;
import com.pickle.ourgames.ui.viewmodels.DetailViewModel;

public class AddDialog extends BottomSheetDialogFragment {
    private String title;
    private TextView txtViewTitle;
  //  Boolean liked;
    String uid;
    int gameId;


    ImageView likeBtn ;
    ImageView playLaterIcon;
    ImageView alreadyPlayedIcon;
    ImageView addReviewImg;
    private DetailViewModel viewModel;
    private RatingBar ratingBar;


   private PlayLaterLisener playLaterLisener;
   private StarredListener starredListener;
    public interface AlreadyPlayedClickLisener {
       void selected(Boolean isAlreadyPlayed);
    }


    public interface PlayLaterLisener{
        void selected(Boolean isPlayLater);
    }
    public interface StarredListener{
        void starred(double numStars);
    }


    public void setPlayLaterLisener(PlayLaterLisener playLaterLisener) {
        this.playLaterLisener = playLaterLisener;
    }

    public void setStarredListener(StarredListener starredListener) {
        this.starredListener = starredListener;
    }

    public AddDialog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static AddDialog newInstance(String title){
         AddDialog frag = new AddDialog();

        Bundle args = new Bundle();


         frag.setTitle(title);
         frag.setArguments(args);

        return frag;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

         View view = inflater.inflate(R.layout.add_dialog,container);
        alreadyPlayedIcon = view.findViewById(R.id.img_played);
         ratingBar = view.findViewById(R.id.ratingbar_dialog_detail);
        playLaterIcon = view.findViewById(R.id.img_play_later);
         txtViewTitle = view.findViewById(R.id.txt_dialog_title)   ;
         likeBtn = view.findViewById(R.id.img_heart_icon_dialog_detail);
         addReviewImg = view.findViewById(R.id.img_add_review_dialog_detail);

         return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


       uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

       gameId =  SharedPref.read(SharedPref.GAME_NAME,0);

        viewModel =  ViewModelProviders.of(this.getActivity()).get(DetailViewModel.class);
        Log.i("Viewmodel dialog", viewModel.toString());
        txtViewTitle.setText(title);




        setUpNavigateOnAddReviewClick();


        listenToIsPlayLaterAndSetUpView();

        listenToAlreadyPlayedAndSetUpView();

        listenToLikedAndSetUpLikeView();

        handleAlreadyPlayedClick();
        handleRatingBarClick();
        handlePlayLaterClick();

        handleLikeBtnClick();
    }

    private void listenToIsPlayLaterAndSetUpView() {
        viewModel.listenToPlayLaterCollection(gameId, uid).observe(this, value -> {
            Log.i("Play later", String.valueOf(value.getPlayLater()));
            if (value != null){
                if (value.getPlayLater()){
                    playLaterIcon.setBackgroundResource(R.drawable.red_clock_transparent);
                    playLaterIcon.setTag("is_play_later");

                }else{
                    playLaterIcon.setBackgroundResource(R.drawable.white_transparent_clock);
                    playLaterIcon.setTag("not_play_later");
                }
            }
        });
    }

    private void listenToAlreadyPlayedAndSetUpView() {
        viewModel.listenToAlredyPlayed(gameId,uid).observe( this, value -> {



            if (value != null){
                if (value.isAlreadyPlayed()) {
                    alreadyPlayedIcon.setBackgroundResource(R.drawable.game_icon_red);
                    alreadyPlayedIcon.setTag("played");
                } else {
                    alreadyPlayedIcon.setBackgroundResource(R.drawable.game_icon_white);
                    alreadyPlayedIcon.setTag("not_played");
                }
        }

        });
    }

    private void setUpNavigateOnAddReviewClick() {
        addReviewImg.setOnClickListener(click ->{
            FragmentTransaction trans = getFragmentManager().beginTransaction();

            trans.replace(R.id.containerView, new ReviewsFragment());
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            trans.addToBackStack(null);
            trans.commit();

            Fragment prev = getFragmentManager().findFragmentByTag("fragment_add_dialogue");
            if (prev != null) {
                BottomSheetDialogFragment df = (BottomSheetDialogFragment) prev;
                df.dismiss();
            }


        });
    }

    private void handleAlreadyPlayedClick() {
        alreadyPlayedIcon.setOnClickListener(click ->{
            if (alreadyPlayedIcon.getTag() == "not_played"){

                viewModel.addAlreadyPlayedGame(gameId,uid,true);

            }else{


                    viewModel.addAlreadyPlayedGame(gameId, uid, false);
            }

        });

    }

    private void listenToLikedAndSetUpLikeView() {


        viewModel.listenToLiked(gameId, uid).observe(this, value ->{

            Log.i("Dialog val", String.valueOf(value.getLiked()));

            if(value != null) {

                if (value.getLiked()) {
                    likeBtn.setBackgroundResource(R.drawable.flat_red_heart);
                    likeBtn.setTag("liked");


                } else {
                    likeBtn.setBackgroundResource(R.drawable.heart_icon);
                    likeBtn.setTag("not_liked");

                }
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void handlePlayLaterClick() {
        playLaterIcon.setOnClickListener(click ->{

            if(playLaterIcon.getTag() == "not_play_later"){
                viewModel.addPlayLater(gameId,uid,true);
            }else{
                viewModel.addPlayLater(gameId,uid,false);
            }

        });
    }

    private void handleRatingBarClick() {
        ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser){

                double numStars = rating;
                    ratingBar.setRating(rating);
                starredListener.starred(numStars);
            }
        });
    }

    private void handleLikeBtnClick() {

        likeBtn.setOnClickListener(click -> {

                if(!(likeBtn.getTag() == "liked" )){


                    viewModel.addGameLiked(gameId, uid,true);

                   // likedClickLisener.selected(false);

                }else{

                    viewModel.addGameLiked(gameId, uid,false);
                  //  likedClickLisener.selected(true);
                }
            });


    }
}


//heart icon attribution <a href="http://cliparts.co/clipart/2776020">Clip arts</a>

//clock icon <div>Icons made by <a href="https://www.flaticon.com/authors/chanut" title="Chanut">Chanut</a> from <a href="https://www.flaticon.com/"
// title="Flaticon">www.flaticon.com</a></div>


//video game icon--- <a href="https://www.freeiconspng.com/img/4478">Games icons, free icons in Token Dark</a>
// <a href="https://www.freeiconspng.com/img/4478"
// title="Image from freeiconspng.com">
// <img src="https://www.freeiconspng.com/uploads/games-icons-free-icons-in-token-dark-2.png" width="350" alt="Games icons, free icons in Token Dark" /></a>

