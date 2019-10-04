package com.pickle.ourgames.ui.viewmodels;

import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.pickle.ourgames.OurGamesApplication;
import com.pickle.ourgames.model.Covers;
import com.pickle.ourgames.model.Games;
import com.pickle.ourgames.repository.GamesRepository;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PopularViewModel extends ViewModel {

    private GamesRepository repo;
    private MutableLiveData<List<Games>> gamesMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Covers>> coversLiveData = new MutableLiveData<>();
private MutableLiveData<TitleAnDCoverContainer> container = new MutableLiveData<TitleAnDCoverContainer>();



public PopularViewModel(){
        this.repo = OurGamesApplication.injectRepo();
    }

    public LiveData<List<Games>> getGamesMutableLiveData() {
        return gamesMutableLiveData;
    }





    public MutableLiveData<List<Covers>> getCoversLiveData() {
        return coversLiveData;
    }

    public void loadGamesByPopularity(RequestBody body){
        repo.getGamesOrderByPopularity(body).subscribeOn(Schedulers.io()).doOnError(err ->{
            err.printStackTrace();
            Log.d("ErrorHere", err.getMessage());
        }).retry(2)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( result ->{
                        gamesMutableLiveData.postValue(result);

                });
    }


    public MutableLiveData<TitleAnDCoverContainer> getContainer() {
        return container;
    }

    public void loadCoversById(RequestBody body){




        repo.getGamesOrderByPopularity(body).subscribeOn(Schedulers.io()).doOnError(err -> err.printStackTrace()).flatMapIterable(list -> list)
                .map(item -> item.getCover().toString()).toList().flatMap(games -> Single.fromObservable(repo.getCovers( RequestBody.create(MediaType.parse("text/plain"),
                "fields *; where id = (" + TextUtils.join("," , games  ) +");")).doOnError(err -> err.printStackTrace()))).observeOn(AndroidSchedulers.mainThread()).subscribe(it -> coversLiveData.postValue(it));
}

public class TitleAnDCoverContainer{
        private List<Covers> coverList;
        private List<String> titleList;


    public List<Covers> getCoverList() {
        return coverList;
    }

    public void setCoverList(List<Covers> coverList) {
        this.coverList = coverList;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }
}


    public static String intArrayToString(List<String> coverList){
        StringBuilder coverListString = null;

       for(String item : coverList){

          coverListString.append( item);
       }
        Log.d("CoversList" , coverListString.toString());
        return coverListString.toString();
    }




}
 /*  */




   /* Fetch Title and Cover
       TitleAnDCoverContainer containerList = new TitleAnDCoverContainer();
   List<String> titleList = new ArrayList<>();

        repo.getGamesOrderByPopularity(body).subscribeOn(Schedulers.io()).doOnError(err -> err.printStackTrace()).flatMapIterable(list -> list)
                .map(item -> {
                titleList.add(item.getName().length() >1?item.getName():"none");
                return item.getCover().toString();
                }).toList().flatMap(games -> Single.fromObservable(repo.getCovers( RequestBody.create(MediaType.parse("text/plain"),
                "fields *; where id = (" + TextUtils.join("," , games  ) +");")).doOnError(err -> err.printStackTrace()))).map(covers ->{
                containerList.setCoverList(covers);
                containerList.setTitleList(titleList);
                return containerList;

                } ).observeOn(AndroidSchedulers.mainThread()).subscribe(it -> container.postValue(it));*/