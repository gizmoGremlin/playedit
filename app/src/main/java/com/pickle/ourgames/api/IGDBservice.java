package com.pickle.ourgames.api;

import com.pickle.ourgames.BuildConfig;
import com.pickle.ourgames.model.Covers;
import com.pickle.ourgames.model.Games;
import com.pickle.ourgames.model.Genres;
import com.pickle.ourgames.model.ScreenShot;
import com.pickle.ourgames.model.TimeToBeat;
import com.pickle.ourgames.model.db_model.Keyword;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;

import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IGDBservice {

    @Headers({
            "Accept: application/json",
            "user-key:" + BuildConfig.IGBD_KEY
    })

    @POST("/games")
    Observable<List<Games>> getGamesOrderByPopularity(@Body RequestBody body);
    @Headers({
            "Accept: application/json",
            "user-key:" + BuildConfig.IGBD_KEY
    })
    @POST("/keywords")
    Observable<List<Keyword>> getKeywords(@Body RequestBody body);
    @Headers({
            "Accept: application/json",
            "user-key:" + BuildConfig.IGBD_KEY
    })
    @POST("/covers")
    Observable<List<Covers>> getCoversById(@Body RequestBody body);

    @Headers({
            "Accept: application/json",
            "user-key:" + BuildConfig.IGBD_KEY
    })
    @POST("/games")
    Observable<List<Games>> getGameById(@Body RequestBody body);

    @Headers({
            "Accept: application/json",
            "user-key:" + BuildConfig.IGBD_KEY
    })
    @POST("/screenshots")
    Observable<List<ScreenShot>> getScreenShotByIds(@Body RequestBody body);

    @Headers({
            "Accept: application/json",
            "user-key:" + BuildConfig.IGBD_KEY
    })
    @POST("/time_to_beats")
    Observable<List<TimeToBeat>> getTimeToBeatByIds(@Body RequestBody body);


    @Headers({
            "Accept: application/json",
            "user-key:" + BuildConfig.IGBD_KEY
    })
    @POST("/genres")
    Observable<List<Genres>> getGenresByIds(@Body RequestBody body);





}
