package com.pickle.ourgames.model.db_model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;


public class Review {

    private String uid;
    private int gameId;
    private String userReview;
    @ServerTimestamp
    private Date time;
    public Review() {
    }

    public Review(String uid, int gameId, String userReview) {
        this.uid = uid;
        this.gameId = gameId;
        this.userReview = userReview;


    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }
}
