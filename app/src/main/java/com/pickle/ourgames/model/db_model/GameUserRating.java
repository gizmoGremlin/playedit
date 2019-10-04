package com.pickle.ourgames.model.db_model;

public class GameUserRating {

    private Integer gameId;
    private String userId;
    private Boolean liked;
    private Double numStars;
    private Integer timesRated;
    private Boolean wantToPlay;
    private Integer oneStarCount;
    private Integer twoStarredCount;
    private Integer threeStarCount;
    private Integer fourStarCount;
    private Integer fiveStarCount;

    public GameUserRating(Integer gameId, String userId, Boolean liked,
                          Double numStars, Integer timesRated, Boolean wantToPlay) {
        this.gameId = gameId;
        this.userId = userId;
        this.liked = liked;
        this.numStars = numStars;
        this.timesRated = timesRated;
        this.wantToPlay = wantToPlay;

    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public Double getNumStars() {
        return numStars;
    }

    public void setNumStars(Double numStars) {
        this.numStars = numStars;
    }

    public Integer getTimesRated() {
        return timesRated;
    }

    public void setTimesRated(Integer timesRated) {
        this.timesRated = timesRated;
    }

    public Boolean getWantToPlay() {
        return wantToPlay;
    }

    public void setWantToPlay(Boolean wantToPlay) {
        this.wantToPlay = wantToPlay;
    }


}
