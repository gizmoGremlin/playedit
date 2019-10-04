package com.pickle.ourgames.ui.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pickle.ourgames.OurGamesApplication;
import com.pickle.ourgames.model.Games;
import com.pickle.ourgames.model.PopularDetailContainer;
import com.pickle.ourgames.model.db_model.AggregateRatings;
import com.pickle.ourgames.model.db_model.AlreadyPlayed;
import com.pickle.ourgames.model.db_model.LikedGame;
import com.pickle.ourgames.model.db_model.PlayLater;
import com.pickle.ourgames.model.db_model.ProfilePic;
import com.pickle.ourgames.repository.GamesRepository;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class DetailViewModel extends ViewModel {
    private MutableLiveData<List<Games>> gameIdLiveData = new MutableLiveData();
    private GamesRepository repo;
    private MutableLiveData<LikedGame> likedGameLiveData = new MutableLiveData<>();
    private MutableLiveData<PopularDetailContainer> detailContainer = new MutableLiveData<>();
    private MutableLiveData<List<LikedGame>> listLikedLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLiked = new MutableLiveData<>();
    private MutableLiveData<Boolean> isPlayLater = new MutableLiveData<>();
    private MutableLiveData<Boolean> isAlreadyPlayed = new MutableLiveData<>();
    private MutableLiveData<Boolean> shouldHideDialog = new MutableLiveData<>();
    private MutableLiveData<Integer> numPeopleAlreadyPlayed = new MutableLiveData<>();
    private MutableLiveData<Integer> numPeopleReviewLiveData = new MutableLiveData<>();
    private Disposable profilePicFlowableSub =null;
    private Disposable sub = null;
    private Disposable likedGameSub = null;
    private Disposable compositeSub = null;
    private Disposable aggregateSub = null;
    private Disposable listLikedSub = null;
    private MutableLiveData<List<ProfilePic>> pictureListLiveData = new MutableLiveData<>();
    private MutableLiveData<AggregateRatings> ratingsMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getNumPeopleReviewLiveData() {
        return numPeopleReviewLiveData;
    }

    public DetailViewModel(){
        this.repo = OurGamesApplication.injectRepo();
    }

    public MutableLiveData<Boolean> getShouldHideDialog() {
        return shouldHideDialog;
    }

    public void setShouldHideDialog(Boolean shouldHide){
        shouldHideDialog.postValue(shouldHide);
    }

    public MutableLiveData<Boolean> getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean liked){
        isLiked.postValue(liked);
    }

    public MutableLiveData<Boolean> getIsPlayLater() {
        return isPlayLater;
    }

    public void setIsPlayedLater(Boolean playLater){
        isPlayLater.postValue(playLater);
    }

    public MutableLiveData<Boolean> getIsAlreadyPlayed() {
        return isAlreadyPlayed;
    }

    public void setIsAlreadyPlayed(Boolean isAlreadyPlayed) {
        this.isAlreadyPlayed.postValue( isAlreadyPlayed);
    }

    public MutableLiveData<AggregateRatings> getRatingsMutableLiveData() {
        return ratingsMutableLiveData;
    }

    public MutableLiveData<List<ProfilePic>> getPictureListLiveData() {
        return pictureListLiveData;
    }

    public MutableLiveData<LikedGame> getLikedGameLiveData() {
        return likedGameLiveData;
    }

    public MutableLiveData<List<LikedGame>> getListLikedLiveData() {
        return listLikedLiveData;
    }

    public MutableLiveData<PopularDetailContainer> getDetailContainer() {
        return detailContainer;
    }

    public MutableLiveData<List<Games>> getGameIdLiveData() {
        return gameIdLiveData;
    }

    public Disposable getSub() {
        return sub;
    }

    public void setSub(Disposable sub) {
        this.sub = sub;
    }

    public void loadAlreadyPlayedGame(int gameId, String uid){
        repo.loadAlreadyPlayedGame(gameId, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(e -> Log.d("AlreadyPlayVMErr" ,e.getMessage()))
                .subscribe(item ->{
                  isAlreadyPlayed.postValue(item.isAlreadyPlayed());
                });
    }
    public void loadPlayLaterGame(int gameId, String uid){
        repo.loadPlayLaterGame(gameId, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnError(e-> Log.d("PlayLaterErrViewmodel", e.getMessage()))
                .subscribe(item ->{
                    isPlayLater.postValue(item.getPlayLater());
                });
    }
    public void loadNumAlreadyPlayed(int gameid){
        repo.loadNumAlreadyPlayed(gameid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(e -> Log.i("num already played:",  e.getMessage()))
                .subscribe(item -> {
                    numPeopleAlreadyPlayed.postValue(item);
                });
    }

    public void loadNumReviews(int gameId){
        repo.loadNumReviews(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(e ->Log.i("Get Review size", e.getMessage()))
                .subscribe(item -> {
                    numPeopleReviewLiveData.postValue(item);
                });
    }


    public MutableLiveData<Integer> getNumPeopleAlreadyPlayed() {
        return numPeopleAlreadyPlayed;
    }

    public LiveData<AlreadyPlayed> listenToAlredyPlayed(int gameId, String uid){
        return repo.listenToAlredyPlayed(gameId,uid);
    }
    public LiveData<LikedGame> listenToLiked(int gameId, String uid){
       return repo.listenToLikedCollection(gameId, uid);
    }

    public LiveData<PlayLater>  listenToPlayLaterCollection(int gameId, String uid){
        return repo.listenToPlayLaterCollection(gameId, uid);
    }

    public void addAlreadyPlayedGame(int gameId, String uid, Boolean isAlreadyPlayed){
        repo.addAlreadyPlayedGame(gameId,uid,isAlreadyPlayed);
        this.isAlreadyPlayed.postValue(isAlreadyPlayed);
    }


    public void getGameById(RequestBody body){
       sub =  repo.getGameById(body).subscribeOn(Schedulers.io()).doOnError(err ->{
             err.printStackTrace();
             Log.d("ErrorHere", err.getMessage());
         }).retry(2).observeOn(AndroidSchedulers.mainThread()).subscribe(gameId ->{
             gameIdLiveData.postValue(gameId);
       });

    }
    public void loadAggregateGameStats(int gameId){
  aggregateSub =      repo.getAggregatedRatings(gameId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(item ->{
     ratingsMutableLiveData.postValue(item);
  });
    }

    public void addStarredGame(double numStars, int gameId, String userId){
        repo.addStarredGame(numStars, gameId, userId);
    }

    public void addPlayLater(int gameId, String uid, boolean isPlayLater){
        repo.addPlayLater(gameId, uid, isPlayLater);
        setIsPlayedLater(isPlayLater);
    }

    public void addGameLiked(int gameId,String userId, Boolean liked){


        repo.addGameLiked(gameId, userId, liked);
        setIsLiked(liked);
    }
    public void loadLikedGame(int gameId,String userId){
        likedGameSub = repo.getLikedGame(gameId, userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(likedGame -> {
            likedGameLiveData.postValue(likedGame);
        });
    }
    public void loadThoseWhoLikeTheGame(int gameId){
        listLikedSub = repo.getThoseWhoLikeGame(gameId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(listLiked->{
           listLikedLiveData.postValue(listLiked);
        });
    }

    public void loadCombinedContainer(RequestBody timeToBeatBody, RequestBody screenShotBody, RequestBody genreBody,RequestBody coversBody){
        compositeSub = repo.getZippedDetails(timeToBeatBody,screenShotBody,genreBody,coversBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe( composite ->{
            detailContainer.postValue(composite);
                }
        );
    }
        public void loadProfilePicList(List<LikedGame> list){
        profilePicFlowableSub = repo.loadListProfileImages(list).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(picList ->{
            pictureListLiveData.postValue(picList);
        });
        }
        public void addReview(int gameId, String uid, String review){
        repo.addReview(gameId, uid, review);
        }

    @Override
    protected void onCleared() {
        super.onCleared();
        if( !sub.isDisposed()){
            sub.dispose();
        }
    }
}
