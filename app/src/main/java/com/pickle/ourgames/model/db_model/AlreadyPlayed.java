package com.pickle.ourgames.model.db_model;

public class AlreadyPlayed {

    private String uid;
    private int gameId;
    private boolean isAlreadyPlayed;


    public AlreadyPlayed() {
    }

    public AlreadyPlayed(String uid, int gameId, boolean isAlreadyPlayed) {
        this.uid = uid;
        this.gameId = gameId;
        this.isAlreadyPlayed = isAlreadyPlayed;
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

    public boolean isAlreadyPlayed() {
        return isAlreadyPlayed;
    }

    public void setAlreadyPlayed(boolean alreadyPlayed) {
        isAlreadyPlayed = alreadyPlayed;
    }
}
