package com.pickle.ourgames.repository;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.igdb.api_android_java.wrapper.IGDBWrapper;
import com.pickle.ourgames.api.IGDBservice;
import com.pickle.ourgames.model.Covers;
import com.pickle.ourgames.model.Games;
import com.pickle.ourgames.model.Genres;
import com.pickle.ourgames.model.PopularDetailContainer;
import com.pickle.ourgames.model.ScreenShot;
import com.pickle.ourgames.model.TimeToBeat;
import com.pickle.ourgames.model.db_model.AggregateRatings;
import com.pickle.ourgames.model.db_model.AlreadyPlayed;
import com.pickle.ourgames.model.db_model.GameUserRating;
import com.pickle.ourgames.model.db_model.LikedGame;
import com.pickle.ourgames.model.db_model.PlayLater;
import com.pickle.ourgames.model.db_model.ProfilePic;
import com.pickle.ourgames.model.db_model.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;


public class GamesRepository {
    private IGDBservice service;
    private IGDBWrapper wrapper;
    FirebaseFirestore db;
    FirebaseStorage store;
    StorageReference storeRef;
    CollectionReference gameRef;
    CollectionReference starsRef;
    CollectionReference likedRef;
    CollectionReference profilePicRef;
    CollectionReference playLaterRef;
    CollectionReference alreadyPlayedRef;
    CollectionReference reviewReference;
    public GamesRepository(IGDBservice igdBservice, FirebaseFirestore db, FirebaseStorage store) {
        this.service = igdBservice;
        //  wrapper = new IGDBWrapper(context,BuildConfig.IGBD_KEY, Version.STANDARD,false);
        this.db = db;
        this.store = store;
        gameRef = db.collection("games");
        playLaterRef=  db.collection("play_later");
        starsRef = db.collection("stars");
        likedRef = db.collection("liked");
       profilePicRef = db.collection("profile_pic");
       alreadyPlayedRef = db.collection("already_played");
       reviewReference = db.collection("review");


        this.storeRef = store.getReference();


    }

    public Observable<List<Covers>> getCovers(RequestBody body) {
        return service.getCoversById(body);
    }

    public Observable<List<Games>> getGamesOrderByPopularity(RequestBody body) {
        return service.getGamesOrderByPopularity(body);
    }


    public Observable<List<Games>> getGameById(RequestBody body) {
        return service.getGameById(body);
    }
    //Zipped

    public Observable<PopularDetailContainer> getZippedDetails(RequestBody timeToBeatBody, RequestBody screenShotBody, RequestBody genreBody, RequestBody coverBody) {
        return Observable.zip(getGenresByIds(genreBody).subscribeOn(Schedulers.newThread()),
                getScreeenShotsByIds(screenShotBody).subscribeOn(Schedulers.newThread()),
                getTimeToBeatByIds(timeToBeatBody).subscribeOn(Schedulers.newThread()), getCovers(coverBody),
                PopularDetailContainer::new).observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<TimeToBeat>> getTimeToBeatByIds(RequestBody body) {
        return service.getTimeToBeatByIds(body);
    }

    public Observable<List<ScreenShot>> getScreeenShotsByIds(RequestBody body) {
        return service.getScreenShotByIds(body);
    }

    public Observable<List<Genres>> getGenresByIds(RequestBody body) {
        return service.getGenresByIds(body);

    }

/////////////Firebase////////



    public void addReview(int gameId,String uid, String review){

        Review userReview =  new Review(uid,gameId,review);

        reviewReference.add(userReview).addOnFailureListener(e -> Log.i("Add Review fail", e.getMessage()));

    }
    public Single<Integer> loadNumReviews(int gameId){
    return Single.create(emitter -> {
       reviewReference
       .whereEqualTo("gameId", gameId)
       .get()
               .addOnFailureListener(e-> Log.i("reviewNumFail",e.getMessage()))
       .addOnSuccessListener(task -> {
          if(!task.isEmpty()){
              List<Integer> list = new ArrayList<>();
              for (QueryDocumentSnapshot doc: task){
                  list.add(1);
              }
              emitter.onSuccess(list.size());
          }else{
              emitter.onSuccess(0);
          }
       }).addOnFailureListener(e -> Log.i("num reviews", e.getMessage()));
    });
}

    public Single<Integer> loadNumAlreadyPlayed(int gameId){
       return Single.create( emitter -> {
            alreadyPlayedRef
                    .whereEqualTo("gameId", gameId)
                    .get()
                    .addOnSuccessListener(task -> {
                        if (!task.isEmpty()){
                            List<Integer> list = new ArrayList<>();
                           for (QueryDocumentSnapshot doc: task){
                               list.add(1);
                           }
                           emitter.onSuccess(list.size());
                        }else{
                            emitter.onSuccess(0);
                        }
                    }).addOnFailureListener(e -> Log.i("How many reviews", e.getMessage()));
        });

    }

    public LiveData<AlreadyPlayed> listenToAlredyPlayed(int gameId, String uid){
        MutableLiveData<AlreadyPlayed> alreadyPlayed = new MutableLiveData<>();

        alreadyPlayedRef.whereEqualTo("gameId", gameId)
                .whereEqualTo("uid", uid)
                .addSnapshotListener(((snapshots, e) -> {
                    if (e != null) {
                        Log.i("AlredyPlayed repo", e.getMessage());
                    }
                    for (QueryDocumentSnapshot doc : snapshots) {
                        if (doc.exists()) {

                            AlreadyPlayed game = doc.toObject(AlreadyPlayed.class);
                            alreadyPlayed.postValue(game);
                        }
                    }

                }));

        return  alreadyPlayed;
    }

    public LiveData<PlayLater> listenToPlayLaterCollection(int gameId, String uid){
        MutableLiveData<PlayLater> later = new MutableLiveData<>();

        playLaterRef
                .whereEqualTo("gameId", gameId)
                .whereEqualTo("uid", uid)
                .addSnapshotListener(((snapshots, e) -> {
                    if(e != null){
                        Log.i("play later repo", e.getMessage());
                    }
                    for(QueryDocumentSnapshot doc: snapshots){
                        if(doc.exists()){
                            PlayLater playLater = doc.toObject(PlayLater.class);
                            later.postValue(playLater);
                        }
                    }
                }));
                return later;
    }
    public LiveData<LikedGame> listenToLikedCollection(int gameId, String uid ){
        MutableLiveData<LikedGame> liked = new MutableLiveData();


            likedRef.whereEqualTo("gameId", gameId)
                    .whereEqualTo("userId", uid)

                    .addSnapshotListener((snapshots, e) -> {

                        if (e != null) {
                            Log.i("Liked repo Livedata", e.getMessage());
                        }
                        for (QueryDocumentSnapshot doc : snapshots) {
                            Log.i("Liked repo Livedata doc", doc.getId());
                            if (doc.exists()) {

                                LikedGame game = doc.toObject(LikedGame.class);
                                liked.postValue(game);
                            }
                        }


                    });


        return liked;

    }




    public Single<List<ProfilePic>> loadListProfileImages(List<LikedGame> likedGameList){
        List<Single<ProfilePic>> observables = new ArrayList<>();

        for (LikedGame likedGame: likedGameList){
           observables.add( getProfilePicForUser(likedGame.getUserId()).subscribeOn(Schedulers.newThread()));
        }


       Single<List<ProfilePic>> profilePicFlowable = Single.merge(observables).toList();

        return profilePicFlowable;
    }



    public Single<ProfilePic> getProfilePicForUser(String uid){
       return Single.create(emitter -> {
            profilePicRef
                    .whereEqualTo("uid", uid)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()){
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots){
                                emitter.onSuccess(doc.toObject(ProfilePic.class));
                            }
                        }
                    }).addOnFailureListener(e -> Log.i("Profile pic err:", e.getMessage()));
        });
    }

    public void uploadProfilePicReturnReference(Uri image, String uid){
        StorageReference imageRef = storeRef.child("profile_photos/" + uid +"/"+ uid +".jpeg");
        UploadTask uploadTask = imageRef.putFile(image);

        uploadTask.addOnFailureListener(e -> Log.i("Image Upload fail:", e.getMessage()))
                .addOnSuccessListener(taskSnapshot -> {
                    Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                        if(!task.isSuccessful()){
                            Log.i("Image upload task fail!", task.getException().getMessage());
                        }
                        return imageRef.getDownloadUrl();
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                Uri downloadUri = task.getResult();
                                Log.i("Image upload url", downloadUri.toString());
                                //load file to firestore here
                                addProfilePic(uid, downloadUri.toString());

                            }else {
                                Log.i("Image upload fail", task.getException().getMessage());
                            }
                        }
                    });
                });

    }



    public void addProfilePic(String uid,String refToPic){

        ProfilePic params = new ProfilePic(uid, refToPic);

        profilePicRef
                .whereEqualTo("uid", uid)
                .get().addOnSuccessListener(task ->{
                if (!task.isEmpty()){
                    for (QueryDocumentSnapshot doc : task){
                        profilePicRef.document(doc.getId()).set(params, SetOptions.merge());
                    }
                }else {
                    profilePicRef.add(params)
                            .addOnSuccessListener(ref -> Log.d("Profile Pic success:", ref.getId()))
                            .addOnFailureListener(e-> Log.d("Profile Pic fail:", e.getMessage()));
                }
        });


    }

    public void addGame(int gameId, String userId) {
        Map<String, String> gameParams = new HashMap<>();
        gameParams.put("game_id", String.valueOf(gameId));
        gameParams.put("user_id", userId);

        gameRef.add(gameParams).addOnSuccessListener(documentReference -> Log.d("AddGame", "DocumentSnapshot written with ID: " + documentReference.getId()))
                .addOnFailureListener(Throwable::printStackTrace);
    }

    public void addPlayLater(int gameId, String uid, boolean isPlayLater){

        playLaterRef
                .whereEqualTo("gameId", gameId)
                .whereEqualTo("uid", uid)
                .get()
                .addOnSuccessListener(snapshots -> {
                    if (!snapshots.isEmpty()){
                        for (QueryDocumentSnapshot doc: snapshots){
                            playLaterRef
                                    .document(doc.getId())
                                    .update("playLater", isPlayLater)
                                    .addOnFailureListener(e -> Log.i("PlayLater repo err", e.getMessage()));
                        }

                    }else {
                        PlayLater paramsObj = new PlayLater(isPlayLater,uid,gameId);
                        playLaterRef
                                .add(paramsObj)
                                .addOnSuccessListener(game -> Log.i("Play Later add repo", game.getId()))
                                .addOnFailureListener(e -> Log.i("Play Later repo err2", e.getMessage()));
                    }
                });
    }
     public void addAlreadyPlayedGame(int gameId, String userId,boolean isAlreadyPlayed){
        alreadyPlayedRef
                .whereEqualTo("gameId", gameId)
                .whereEqualTo("uid",userId)
                .get()
                .addOnSuccessListener(task ->{
                    if (!task.isEmpty()){
                        for (QueryDocumentSnapshot doc : task){
                            alreadyPlayedRef.document(doc.getId()).update("alreadyPlayed", isAlreadyPlayed).addOnSuccessListener(ref ->{

                            }).addOnFailureListener(e ->Log.i("AlreadyPlayed Upfail", e.getMessage()));
                        }
                    }else {
                        AlreadyPlayed playedGame = new AlreadyPlayed(userId,gameId, isAlreadyPlayed);
                        alreadyPlayedRef.add(playedGame).addOnSuccessListener(gameRef -> Log.d("played Game Added:", gameRef.getId())).addOnFailureListener(e -> Log.d("played Failure: ", e.getMessage()));
                    }
                }).addOnFailureListener(e-> Log.i("isAlreadyPlayed fail", e.getMessage()));

     }
    public void addGameLiked(int gameId, String userId,boolean isLiked){
        Log.i("Liked Repo", String.valueOf(isLiked));
        likedRef
                .whereEqualTo("gameId", gameId)
                .whereEqualTo("userId",userId)
                .get()
                .addOnSuccessListener(task ->{
            if (!task.isEmpty()){
                for (QueryDocumentSnapshot doc : task){
                    likedRef.document(doc.getId()).update("liked", isLiked).addOnSuccessListener(ref ->{

                    }).addOnFailureListener(e -> e.printStackTrace());
                }
            }else {
                LikedGame likedGame = new LikedGame(gameId, userId, isLiked);
                likedRef.add(likedGame).addOnSuccessListener(gameRef -> Log.d("Liked Game Added:", gameRef.getId())).addOnFailureListener(e -> Log.d("Liked Failure: ", e.getMessage()));
            }
        });

    }
    public Observable<AlreadyPlayed> loadAlreadyPlayedGame(int gameId, String uid){
        return Observable.create(emitter -> {
           alreadyPlayedRef
           .whereEqualTo("gameId", gameId)
           .whereEqualTo("uid", uid)
           .get()
           .addOnSuccessListener(task -> {
               if(!task.isEmpty()){
                   for (QueryDocumentSnapshot doc : task){
                       emitter.onNext(doc.toObject(AlreadyPlayed.class));
                   }
               }
           }).addOnFailureListener(e->Log.i("Already played Get Fail", e.getMessage()));;
        });
    }
    public Observable<PlayLater> loadPlayLaterGame(int gameId, String userId){
        return Observable.create(emitter -> {
            playLaterRef
                    .whereEqualTo("gameId", gameId)
                    .whereEqualTo("userId",userId)
                    .get()
                    .addOnSuccessListener(task->{
                        if(!task.isEmpty()){
                            for (QueryDocumentSnapshot doc : task){

                                emitter.onNext( doc.toObject(PlayLater.class));
                            }
                        }
                    }).addOnFailureListener(e->Log.i("PlayLater Get Fail", e.getMessage()));
        });
    }
    public Observable<LikedGame> getLikedGame(int gameId, String userId){
      return  Observable.create(emitter -> {
            likedRef
                    .whereEqualTo("gameId", gameId)
                    .whereEqualTo("userId",userId)
                    .get()
                    .addOnSuccessListener(task->{
                if(!task.isEmpty()){
                    for (QueryDocumentSnapshot doc : task){

                       emitter.onNext( doc.toObject(LikedGame.class));
                    }
                }
            });
        });


    }
    public Single<List<LikedGame>>  getThoseWhoLikeGame(int gameId){
        return Single.create(emitter -> {
            likedRef.whereEqualTo("gameId", gameId).get().addOnSuccessListener(task ->{
                if(!task.isEmpty()){
                    List likedList = new ArrayList<LikedGame>();
                    for (QueryDocumentSnapshot doc: task){
                       likedList.add( doc.toObject(LikedGame.class));
                    }
                    emitter.onSuccess(likedList);
                }
            }).addOnFailureListener(e -> Log.d("getThoseWhoLikeGameErr:", e.getMessage()));
        });
    }


    public Single<AggregateRatings> getAggregatedRatings(int gameId){
        return Single.create( emitter ->
                starsRef.whereEqualTo("gameId", gameId).get().addOnFailureListener(e-> Log.d("StarSingleErr", e.getMessage())).addOnSuccessListener(task ->{
                    Log.d("RepoStar", "Task Size:"+String.valueOf(task.size()));
                    Log.d("RepoStar", "game Id::"+String.valueOf(gameId));
            if (!task.isEmpty()){
                Gson gson = new Gson();
                int oneStar= 0;
                int twoStar  = 0;
                int threeStar = 0;
                int fourStar = 0;
                int fiveStar = 0;
                int nStars = 0;
                int totalStars = 0;
                double aggragateRating =0;
                for (QueryDocumentSnapshot doc : task){

                   for ( String key :doc.getData().keySet()){

                       if (key.equals("numStars")){

                           Double numStarsDouble = null;
                           if (doc.getData().get(key) instanceof Double) {
                               numStarsDouble = (Double) doc.getData().get(key);
                           }
                           if (doc.getData().get(key) instanceof Integer) {
                               numStarsDouble = Double.valueOf((Integer)doc.getData().get(key));
                           }

                           if (numStarsDouble == null){

                               numStarsDouble = 0.0;
                           }
                           Log.d("RepoStar", "numStarsDouble::"+String.valueOf(numStarsDouble));
                            nStars ++;
                            if (numStarsDouble >= 1.0 && numStarsDouble < 2.0 ){         //g
                                oneStar ++;
                                totalStars += 1;

                            }
                           else if (numStarsDouble >= 2.0 && numStarsDouble < 3.0 ){
                               twoStar ++;
                                totalStars += 2;
                           }
                           else if (numStarsDouble >= 3.0 && numStarsDouble < 4.0 ){
                               threeStar ++;
                                totalStars += 3;
                           }
                           else if (numStarsDouble >= 4.0 && numStarsDouble < 5.0 ){
                                fourStar ++;
                                totalStars += 4;
                            }
                           else if (numStarsDouble == 5.0  ){
                                fiveStar ++;
                                totalStars += 5;
                            }
                           else{
                               oneStar ++;
                               totalStars ++;
                            }



                       }
                   }

                }
                aggragateRating = (nStars!= 0 && totalStars != 0)? totalStars/nStars:0;
                Log.d("RepoStar", "aggragateRating::"+String.valueOf(aggragateRating));
                Log.d("RepoStar", "1 star:"+String.valueOf(oneStar));
                Log.d("RepoStar", "2 Star::"+String.valueOf(twoStar));
                Log.d("RepoStar", "3 Star::"+String.valueOf(threeStar));
                Log.d("RepoStar", "4 star::"+String.valueOf(fourStar));
                Log.d("RepoStar", "5 star::"+String.valueOf(fiveStar));
            emitter.onSuccess(  new AggregateRatings(oneStar,twoStar,threeStar,fourStar,fiveStar, aggragateRating));
            }

        }));
    }


    public void addStarredGame(double numStars, int gameId, String userId) {


        starsRef.whereEqualTo("gameId", gameId)
                .whereEqualTo("userId", userId)
                .get().addOnSuccessListener(task -> {

            if (!task.isEmpty()) {
                ;
                for (QueryDocumentSnapshot doc : task) {
                    //exists.. now update
                    Log.d("RepoUpdate", doc.getId());


                    starsRef.document(doc.getId()).update("numStars", numStars).addOnSuccessListener(ref -> {

                        Log.d("RepoStarsUpdated", doc.getId());

                    }).addOnFailureListener(e -> {
                        e.printStackTrace();
                        Log.d("RepoDetailGame", e.getMessage());
                    });
                }
            } else {
                // doesnt exist so create it

                GameUserRating game = new GameUserRating(gameId, userId, true,
                        numStars, 0, false);

                starsRef.add(game).addOnSuccessListener(sucess -> {
                    Log.d("repogameaddsucess", sucess.getId());
                }).addOnFailureListener(e -> {
                    Log.d("repogameaddfail", e.getMessage());
                });
            }
        }).addOnFailureListener(e -> {
            e.printStackTrace();
            Log.d("RepoDetailError", e.getMessage());
        });

    }

}
