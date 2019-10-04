package com.pickle.ourgames.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.pickle.ourgames.R;
import com.pickle.ourgames.model.Games;
import com.pickle.ourgames.model.db_model.AggregateRatings;
import com.pickle.ourgames.storage.SharedPref;
import com.pickle.ourgames.ui.adapters.PlayedByAdapter;
import com.pickle.ourgames.ui.adapters.SliderAdapterPopularDetail;
import com.pickle.ourgames.ui.viewmodels.DetailViewModel;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.threeten.bp.Instant;
import org.threeten.bp.ZoneId;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class PopularDetailFragment extends Fragment {
    TextView txtViewAggregateScore;
    ImageView coverImg;
    TextView releaseDateTxt;
    String title;
    TextView titleTxt;
    Integer gameId;
    String userUid;
    DetailViewModel viewModel;
    FloatingActionButton fab;
    AddDialog.AlreadyPlayedClickLisener alreadyPlayedClickLisener;
    AddDialog.PlayLaterLisener playLaterLisener;
    AddDialog.StarredListener starredListener;
 //   AddDialog.LikedClickLisener likedClickLisener;
    SliderView sliderImages;
    BarChart barChart;
    RatingBar smallRatingBar;
    RecyclerView rvWatchedBY;
    PlayedByAdapter adapter;
    ImageView likedIcon;
    TextView txtNumLiked;
    TextView txtNumPeoplePlayed;
    TextView txtNumReviews;
    TextView txtSummary;
    TagGroup tagGroup;
    public static PopularDetailFragment newInstance() {
        return new PopularDetailFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // ((AppCompatActivity) getActivity()).findViewById(R.id.tab_layout).setVisibility(View.GONE);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        View v = inflater.inflate(R.layout.fragment_popular_detail, container, false);
        PlayedByAdapter.OnItemClickListener playedBylistener = pic -> {

        };
        rvWatchedBY = v.findViewById(R.id.rv_liked_by_popular_detail);
       // rvWatchedBY = new RecyclerView(getContext());
        adapter = new PlayedByAdapter(playedBylistener,getContext());
        rvWatchedBY.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        rvWatchedBY.setAdapter(adapter);

        connectViews(v);
        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        userUid = auth.getCurrentUser().getUid();
        viewModel = ViewModelProviders.of(this.getActivity()).get(DetailViewModel.class);
        Log.i("Viewmodel detail", viewModel.toString());

        int value = SharedPref.read(SharedPref.GAME_NAME, 0);

        String text = "fields *;where id = (" + value + ");";
        RequestBody body = paramsToRequestBody(text);
        viewModel.getGameById(body);




        viewModel.getGameIdLiveData().observe(this, data -> {


            Games game = data.get(0);
    List<Integer> keyword = game.getKeywords();
        //get keywords


            gameId = game.getId();
            getAndDisplayRaings();
            txtSummary.setText(game.getSummary());
            viewModel.loadAlreadyPlayedGame(gameId, userUid);
            viewModel.getIsAlreadyPlayed().observe(this, alreadyPlayed ->{
             //   viewModel.setIsAlreadyPlayed(alreadyPlayed);
            });


            viewModel.loadPlayLaterGame(gameId,userUid);
            viewModel.getIsPlayLater().observe(this, playLater->{
                Log.i("PlayLater Viewmodel", playLater.toString());
               // viewModel.setIsPlayedLater(playLater);

            });

            viewModel.loadNumAlreadyPlayed(gameId);
            viewModel.getNumPeopleAlreadyPlayed().observe(this, number ->{
                txtNumPeoplePlayed.setText(String.valueOf(number));
            });
            viewModel.loadNumReviews(gameId);
            viewModel.getNumPeopleReviewLiveData().observe(this, number ->{


                txtNumReviews.setText(String.valueOf(number));
            });
                    viewModel.loadLikedGame(gameId, userUid);
            viewModel.getLikedGameLiveData().observe(this, likedGame -> {


                viewModel.setIsLiked(likedGame.getLiked());
            });


            viewModel.loadThoseWhoLikeTheGame(gameId);


            viewModel.getListLikedLiveData().observe(this, likedList -> {
                Log.d("LikedList:", String.valueOf(likedList.size()));

                txtNumLiked.setText(String.valueOf(likedList.size()));
                viewModel.loadProfilePicList(likedList);
                viewModel.getPictureListLiveData().observe(this, items ->{

                adapter.setProfilePicList(items);
                adapter.notifyDataSetChanged();

                    Log.i("Pictures to display!!",items.get(0).getPicPath());

                });


            });


           int releaseDate = game.getFirstReleaseDate();

          int year =   Instant.ofEpochSecond(releaseDate).atZone(ZoneId.systemDefault()).getYear();
            getAndDisplayScreenShots(game, year);


        });



        fab.setOnClickListener(click -> {
            showAddDialog(title);
        });


    }

    private void getAndDisplayScreenShots(Games game, int year) {
        title = game.getName();
        //  Picasso.get().load("http:" )
        titleTxt.setText(title);
        releaseDateTxt.setText(String.valueOf(year));
        //Restrict Screenshot amount to 10 before request
        List<Integer> screenshots = game.getScreenshots();

        if (screenshots.size() > 10) {

            screenshots.subList(9, screenshots.size() - 1).clear();
        }


        RequestBody timeToBeatParams = paramsToRequestBody("fields *;where id =(" + game.getId() + ");");
        RequestBody screenShotParams = paramsToRequestBody("fields *;where id =(" + TextUtils.join(",", screenshots) + ");");
        RequestBody genreParams = paramsToRequestBody("fields *;where id =(" + TextUtils.join(",", game.getGenres()) + ");");
        RequestBody coverParams = paramsToRequestBody("fields *;where id =(" + game.getCover()+ ");");

        viewModel.loadCombinedContainer(timeToBeatParams, screenShotParams, genreParams,coverParams);

        viewModel.getDetailContainer().observe(this, detailItems -> {
               Log.d("Cover", detailItems.getCoversList().get(0).getUrl());

               Picasso.get().load("http:"+ detailItems.getCoversList().get(0).getUrl()).fit().into(coverImg);
            sliderImages.setSliderAdapter(new SliderAdapterPopularDetail(getContext(), detailItems.getScreenShotList()));


        });
    }

    private void getAndDisplayRaings() {
        viewModel.loadAggregateGameStats(gameId);
        viewModel.getRatingsMutableLiveData().observe(this , ratings->{

            txtViewAggregateScore.setText(String.valueOf(ratings.getAggregatedStarRating()));
            smallRatingBar.setRating((float) ratings.getAggregatedStarRating());
            configureBarChart(ratings);

        });
    }

    private void configureBarChart(AggregateRatings ratings) {
        List<BarEntry> chartList = new ArrayList();


        BarEntry entryOne = new BarEntry(1,ratings.getTotalOneStarRatings());
        BarEntry entryTwo = new BarEntry(2, ratings.getTotalTwoStarRatings());
        BarEntry entryThree = new BarEntry(3, ratings.getTotalThreeStarRatings());
        BarEntry entryFour = new BarEntry(4,ratings.getTotalFourStarRatings());
        BarEntry entryFive = new BarEntry(5, ratings.getTotalFiveStarRatings());
        chartList.add(entryOne);
        chartList.add(entryTwo);
        chartList.add(entryThree);
        chartList.add(entryFour);
        chartList.add(entryFive);
        BarDataSet dataSet = new BarDataSet(chartList, "stars");
        dataSet.setColor(getResources().getColor(R.color.white));

        BarData barData =  new BarData(dataSet);
        barChart.getDescription().setEnabled(false);
        barChart.getLegend().setEnabled(false);
        barChart.getXAxis().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.setData(barData);
        barChart.setClickable(false);
        barChart.setVisibility(View.VISIBLE);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onPause() {
        super.onPause();
      //  viewModel.getSub().dispose();
    }

    public static RequestBody paramsToRequestBody(String params) {
        return RequestBody.Companion.create(params, MediaType.parse("text/plain"));
    }


    private void showAddDialog(String title) {
        ConfigueAndInitalizeDialogFragment(title);
    }

    private void ConfigueAndInitalizeDialogFragment(String title) {
        FragmentManager manager = getFragmentManager();

        AddDialog frag = AddDialog.newInstance(title);

        alreadyPlayedClickLisener = ((Boolean isAlreadyPlayed) ->{
            viewModel.addAlreadyPlayedGame(gameId,userUid, isAlreadyPlayed);
        });
     //   frag.setAlreadyPlayedClickLisener(alreadyPlayedClickLisener);

        playLaterLisener =((Boolean isPlayLater) ->{
            Log.i("Play Later Click:",isPlayLater.toString());
            viewModel.addPlayLater(gameId,userUid,isPlayLater);

        });
        frag.setPlayLaterLisener(playLaterLisener);


        starredListener = numStars -> {
            Log.d("Lisener", "Lisener Fired" + numStars);
            viewModel.addStarredGame(numStars,gameId,userUid);
        };
        frag.setStarredListener(starredListener);

        frag.show(manager, "fragment_add_dialogue");
    }

    private void connectViews(View v) {
        titleTxt = v.findViewById(R.id.txt_title_popular_detail);
        fab = v.findViewById(R.id.btn_fab_popular_detail);
        sliderImages = v.findViewById(R.id.img_slider_popular_detail);
        coverImg = v.findViewById(R.id.img_cover_popular_detail);
        releaseDateTxt = v.findViewById(R.id.txt_release_date_popular_detail);
        barChart = v.findViewById(R.id.bar_chart_popular_detail);
        barChart.setFitBars(true);
        barChart.setGridBackgroundColor(ColorTemplate.COLOR_NONE);
        barChart.setBorderColor(ColorTemplate.COLOR_NONE);
        txtViewAggregateScore = v.findViewById(R.id.txt_aggregated_rating_popular_detail);
        smallRatingBar = v.findViewById(R.id.ratingbar_little_popular_detail);
        likedIcon = v.findViewById(R.id.img_liked_popular_detail);
        txtNumLiked = v.findViewById(R.id.txt_num_liked_popular_detail);
        txtNumPeoplePlayed = v.findViewById(R.id.txt_num_played_popular_detail);
        txtNumReviews = v.findViewById(R.id.txt_num_review_popular_detail);
        txtSummary = v.findViewById(R.id.txt_summary_popular_detail);
        tagGroup = v.findViewById(R.id.tag_group_popular_detail);

    }

}
