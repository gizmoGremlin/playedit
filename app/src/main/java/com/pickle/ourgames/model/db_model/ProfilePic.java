package com.pickle.ourgames.model.db_model;

public class ProfilePic {

    private String uid;
    private String picPath;

    public ProfilePic(String uid, String picPath) {
        this.uid = uid;
        this.picPath = picPath;
    }

    public ProfilePic() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
