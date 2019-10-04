package com.pickle.ourgames.model.db_model;

import android.content.Intent;

public class PersistedGame {

    private String title;
    private String cover;
    private String[] screenshots;
    private String[] genres;
    private String[] relatedGames;
    private double rating;


    public PersistedGame(String title, String cover, String[] screenshots, String[] genres, String[] relatedGames) {
        this.title = title;
        this.cover = cover;
        this.screenshots = screenshots;
        this.genres = genres;
        this.relatedGames = relatedGames;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String[] getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(String[] screenshots) {
        this.screenshots = screenshots;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public String[] getRelatedGames() {
        return relatedGames;
    }

    public void setRelatedGames(String[] relatedGames) {
        this.relatedGames = relatedGames;
    }

}
