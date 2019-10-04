package com.pickle.ourgames.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Games {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("aggregated_rating")
    @Expose
    private Double aggregatedRating;
    @SerializedName("aggregated_rating_count")
    @Expose
    private Integer aggregatedRatingCount;
    @SerializedName("category")
    @Expose
    private Integer category;
    @SerializedName("created_at")
    @Expose
    private Integer createdAt;
    @SerializedName("external_games")
    @Expose
    private List<Integer> externalGames = null;
    @SerializedName("game_modes")
    @Expose
    private List<Integer> gameModes = null;
    @SerializedName("genres")
    @Expose
    private List<Integer> genres = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("pulse_count")
    @Expose
    private Integer pulseCount;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("rating_count")
    @Expose
    private Integer ratingCount;
    @SerializedName("screenshots")
    @Expose
    private List<Integer> screenshots = null;
    @SerializedName("similar_games")
    @Expose
    private List<Integer> similarGames = null;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("themes")
    @Expose
    private List<Integer> themes = null;
    @SerializedName("total_rating")
    @Expose
    private Double totalRating;
    @SerializedName("total_rating_count")
    @Expose
    private Integer totalRatingCount;
    @SerializedName("updated_at")
    @Expose
    private Integer updatedAt;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("websites")
    @Expose
    private List<Integer> websites = null;
    @SerializedName("age_ratings")
    @Expose
    private List<Integer> ageRatings = null;
    @SerializedName("alternative_names")
    @Expose
    private List<Integer> alternativeNames = null;
    @SerializedName("collection")
    @Expose
    private Integer collection;
    @SerializedName("cover")
    @Expose
    private Integer cover;
    @SerializedName("first_release_date")
    @Expose
    private Integer firstReleaseDate;
    @SerializedName("franchise")
    @Expose
    private Integer franchise;
    @SerializedName("franchises")
    @Expose
    private List<Integer> franchises = null;
    @SerializedName("involved_companies")
    @Expose
    private List<Integer> involvedCompanies = null;
    @SerializedName("keywords")
    @Expose
    private List<Integer> keywords = null;
    @SerializedName("parent_game")
    @Expose
    private Integer parentGame;
    @SerializedName("platforms")
    @Expose
    private List<Integer> platforms = null;
    @SerializedName("player_perspectives")
    @Expose
    private List<Integer> playerPerspectives = null;
    @SerializedName("release_dates")
    @Expose
    private List<Integer> releaseDates = null;
    @SerializedName("videos")
    @Expose
    private List<Integer> videos = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAggregatedRating() {
        return aggregatedRating;
    }

    public void setAggregatedRating(Double aggregatedRating) {
        this.aggregatedRating = aggregatedRating;
    }

    public Integer getAggregatedRatingCount() {
        return aggregatedRatingCount;
    }

    public void setAggregatedRatingCount(Integer aggregatedRatingCount) {
        this.aggregatedRatingCount = aggregatedRatingCount;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Integer createdAt) {
        this.createdAt = createdAt;
    }

    public List<Integer> getExternalGames() {
        return externalGames;
    }

    public void setExternalGames(List<Integer> externalGames) {
        this.externalGames = externalGames;
    }

    public List<Integer> getGameModes() {
        return gameModes;
    }

    public void setGameModes(List<Integer> gameModes) {
        this.gameModes = gameModes;
    }

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getPulseCount() {
        return pulseCount;
    }

    public void setPulseCount(Integer pulseCount) {
        this.pulseCount = pulseCount;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    public List<Integer> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(List<Integer> screenshots) {
        this.screenshots = screenshots;
    }

    public List<Integer> getSimilarGames() {
        return similarGames;
    }

    public void setSimilarGames(List<Integer> similarGames) {
        this.similarGames = similarGames;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Integer> getThemes() {
        return themes;
    }

    public void setThemes(List<Integer> themes) {
        this.themes = themes;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public Integer getTotalRatingCount() {
        return totalRatingCount;
    }

    public void setTotalRatingCount(Integer totalRatingCount) {
        this.totalRatingCount = totalRatingCount;
    }

    public Integer getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Integer updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Integer> getWebsites() {
        return websites;
    }

    public void setWebsites(List<Integer> websites) {
        this.websites = websites;
    }

    public List<Integer> getAgeRatings() {
        return ageRatings;
    }

    public void setAgeRatings(List<Integer> ageRatings) {
        this.ageRatings = ageRatings;
    }

    public List<Integer> getAlternativeNames() {
        return alternativeNames;
    }

    public void setAlternativeNames(List<Integer> alternativeNames) {
        this.alternativeNames = alternativeNames;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getCover() {
        return cover;
    }

    public void setCover(Integer cover) {
        this.cover = cover;
    }

    public Integer getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public void setFirstReleaseDate(Integer firstReleaseDate) {
        this.firstReleaseDate = firstReleaseDate;
    }

    public Integer getFranchise() {
        return franchise;
    }

    public void setFranchise(Integer franchise) {
        this.franchise = franchise;
    }

    public List<Integer> getFranchises() {
        return franchises;
    }

    public void setFranchises(List<Integer> franchises) {
        this.franchises = franchises;
    }

    public List<Integer> getInvolvedCompanies() {
        return involvedCompanies;
    }

    public void setInvolvedCompanies(List<Integer> involvedCompanies) {
        this.involvedCompanies = involvedCompanies;
    }

    public List<Integer> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Integer> keywords) {
        this.keywords = keywords;
    }

    public Integer getParentGame() {
        return parentGame;
    }

    public void setParentGame(Integer parentGame) {
        this.parentGame = parentGame;
    }

    public List<Integer> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<Integer> platforms) {
        this.platforms = platforms;
    }

    public List<Integer> getPlayerPerspectives() {
        return playerPerspectives;
    }

    public void setPlayerPerspectives(List<Integer> playerPerspectives) {
        this.playerPerspectives = playerPerspectives;
    }

    public List<Integer> getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(List<Integer> releaseDates) {
        this.releaseDates = releaseDates;
    }

    public List<Integer> getVideos() {
        return videos;
    }

    public void setVideos(List<Integer> videos) {
        this.videos = videos;
    }

}