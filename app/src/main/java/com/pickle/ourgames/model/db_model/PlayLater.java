package com.pickle.ourgames.model.db_model;

public class PlayLater {

    private Boolean isPlayLater;
    private String uid;
    private int gameId;

    public PlayLater() {
    }

    public PlayLater(Boolean isPlayLater, String uid, int gameId) {
        this.isPlayLater = isPlayLater;
        this.uid = uid;
        this.gameId = gameId;
    }

    public Boolean getPlayLater() {
        return isPlayLater;
    }

    public void setPlayLater(Boolean playLater) {
        isPlayLater = playLater;
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
}
