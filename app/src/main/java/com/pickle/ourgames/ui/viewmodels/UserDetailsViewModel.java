package com.pickle.ourgames.ui.viewmodels;

import android.net.Uri;

import androidx.lifecycle.ViewModel;

import com.pickle.ourgames.OurGamesApplication;
import com.pickle.ourgames.repository.GamesRepository;

public class UserDetailsViewModel extends ViewModel {
   private GamesRepository repo;
    public UserDetailsViewModel(){
        this.repo = OurGamesApplication.injectRepo();
    }





public void uploadProfilePic(Uri image, String uid){
        repo.uploadProfilePicReturnReference(image, uid);
}

}
