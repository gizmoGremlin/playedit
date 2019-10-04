package com.pickle.ourgames;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.pickle.ourgames.api.IGDBservice;
import com.pickle.ourgames.repository.GamesRepository;
import com.pickle.ourgames.storage.SharedPref;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OurGamesApplication extends Application {

    private static String BASE_URL = "https://api-v3.igdb.com";
    private Retrofit retrofit;
    private IGDBservice igdBservice;
    private  static GamesRepository repo;
    FirebaseFirestore db;
    FirebaseStorage store;


    public static GamesRepository injectRepo(){
        if(repo == null){
            throw new NullPointerException();
        }
        return repo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
        SharedPref.init(this);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(client)
                .build();
        igdBservice = retrofit.create(IGDBservice.class);

        db = FirebaseFirestore.getInstance();
        store = FirebaseStorage.getInstance();

        repo = new GamesRepository(igdBservice, db, store);


    }
}
