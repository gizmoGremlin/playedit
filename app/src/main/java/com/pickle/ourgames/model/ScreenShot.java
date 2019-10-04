package com.pickle.ourgames.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScreenShot {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("game")
    @Expose
    private Integer game;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("image_id")
    @Expose
    private String imageId;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private Integer width;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGame() {
        return game;
    }

    public void setGame(Integer game) {
        this.game = game;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

}
