package com.pickle.ourgames.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeToBeat {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("game")
    @Expose
    private Integer game;
    @SerializedName("normally")
    @Expose
    private Integer normally;

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

    public Integer getNormally() {
        return normally;
    }

    public void setNormally(Integer normally) {
        this.normally = normally;
    }

}
