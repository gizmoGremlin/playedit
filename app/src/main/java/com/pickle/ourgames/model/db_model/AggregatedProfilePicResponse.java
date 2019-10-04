package com.pickle.ourgames.model.db_model;

import java.util.List;

public class AggregatedProfilePicResponse {

    private List<ProfilePic> profilePicList;

    public AggregatedProfilePicResponse(List<ProfilePic> profilePicList) {
        this.profilePicList = profilePicList;
    }
}
