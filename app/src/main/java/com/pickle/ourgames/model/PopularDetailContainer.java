package com.pickle.ourgames.model;

import java.util.List;

public class PopularDetailContainer {

    private List<Genres> genresList;
    private List<ScreenShot> screenShotList;
    private List<TimeToBeat> timeToBeatList;
    private List<Covers>  coversList;
    public PopularDetailContainer( List<Genres> genresList, List<ScreenShot> screenShotList, List<TimeToBeat> timeToBeatList, List<Covers>coversList) {
        this.coversList = coversList;
        this.genresList = genresList;
        this.screenShotList = screenShotList;
        this.timeToBeatList = timeToBeatList;
    }

    public List<Genres> getGenresList() {
        return genresList;
    }

    public List<ScreenShot> getScreenShotList() {
        return screenShotList;
    }

    public List<TimeToBeat> getTimeToBeatList() {
        return timeToBeatList;
    }

    public List<Covers> getCoversList() {
        return coversList;
    }
}
