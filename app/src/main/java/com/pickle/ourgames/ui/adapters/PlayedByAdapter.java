package com.pickle.ourgames.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.pickle.ourgames.R;
import com.pickle.ourgames.model.Covers;
import com.pickle.ourgames.model.db_model.ProfilePic;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayedByAdapter extends RecyclerView.Adapter<PlayedByAdapter.PlayedByViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(ProfilePic pic);
    }
    private Context context;
    private final PlayedByAdapter.OnItemClickListener listener;
    private List<ProfilePic> profilePicList;

    public PlayedByAdapter(OnItemClickListener listener,Context context){
        this.listener = listener;
        this.context = context;
    }

    public void setProfilePicList(List<ProfilePic> profilePicList) {
        this.profilePicList = profilePicList;
    }

    @NonNull
    @Override
    public PlayedByViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_view_popular_detail_watched_by, parent, false);
        Log.i("PlayedBy","onCreateViewHolder:" );
        PlayedByViewHolder vh = new PlayedByViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull PlayedByViewHolder holder, int position) {
        Log.i("PlayedBY","onBindViewHolder");
        holder.bind(profilePicList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        Log.i("PlayedBY","getItemCount" );
        if (profilePicList != null) {
            return profilePicList.size();
        }
        else {
            return 0;
        }
    }

    class PlayedByViewHolder extends RecyclerView.ViewHolder {
        private CircularImageView portrait;
        public PlayedByViewHolder(@NonNull View itemView) {
            super(itemView);
            portrait = itemView.findViewById(R.id.img_liked_portrait_popular_detail);
        }

        public void bind(ProfilePic pics, final OnItemClickListener listener) {

            Picasso.get().load(pics.getPicPath()).placeholder(R.drawable.flat_red_heart).into(portrait, new Callback() {
                @Override
                public void onSuccess() {
                    Log.i("Profile Pic Load err", "Success");
                }

                @Override
                public void onError(Exception e) {
                    Log.i("Profile Pic Load err", e.getMessage());
                }
            });

            listener.onItemClick(pics);
        }

    }
}
