package com.pickle.ourgames.model.db_model;

public class AggregateRatings {

    private int totalOneStarRatings;
    private int totalTwoStarRatings;
    private int totalThreeStarRatings;
    private int totalFourStarRatings;
    private int totalFiveStarRatings;

    private double aggregatedStarRating;

    public AggregateRatings(int totalOneStarRatings, int totalTwoStarRatings, int totalThreeStarRatings, int totalFourStarRatings, int totalFiveStarRatings, double aggregatedStarRating) {
        this.totalOneStarRatings = totalOneStarRatings;
        this.totalTwoStarRatings = totalTwoStarRatings;
        this.totalThreeStarRatings = totalThreeStarRatings;
        this.totalFourStarRatings = totalFourStarRatings;
        this.totalFiveStarRatings = totalFiveStarRatings;
        this.aggregatedStarRating = aggregatedStarRating;
    }

    public int getTotalOneStarRatings() {
        return totalOneStarRatings;
    }

    public void setTotalOneStarRatings(int totalOneStarRatings) {
        this.totalOneStarRatings = totalOneStarRatings;
    }

    public int getTotalTwoStarRatings() {
        return totalTwoStarRatings;
    }

    public void setTotalTwoStarRatings(int totalTwoStarRatings) {
        this.totalTwoStarRatings = totalTwoStarRatings;
    }

    public int getTotalThreeStarRatings() {
        return totalThreeStarRatings;
    }

    public void setTotalThreeStarRatings(int totalThreeStarRatings) {
        this.totalThreeStarRatings = totalThreeStarRatings;
    }

    public int getTotalFourStarRatings() {
        return totalFourStarRatings;
    }

    public void setTotalFourStarRatings(int totalFourStarRatings) {
        this.totalFourStarRatings = totalFourStarRatings;
    }

    public int getTotalFiveStarRatings() {
        return totalFiveStarRatings;
    }

    public void setTotalFiveStarRatings(int totalFiveStarRatings) {
        this.totalFiveStarRatings = totalFiveStarRatings;
    }

    public double getAggregatedStarRating() {
        return aggregatedStarRating;
    }

    public void setAggregatedStarRating(double aggregatedStarRating) {
        this.aggregatedStarRating = aggregatedStarRating;
    }
}
