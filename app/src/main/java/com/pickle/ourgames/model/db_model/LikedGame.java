package com.pickle.ourgames.model.db_model;

public class LikedGame {

    private int gameId;
    private String userId;
    private Boolean isLiked;

    public LikedGame() {
    }

    public LikedGame(int gameId, String userId, Boolean isLiked) {
        this.gameId = gameId;
        this.userId = userId;
        this.isLiked = isLiked;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }
}
