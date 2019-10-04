package com.pickle.ourgames.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pickle.ourgames.R;
import com.pickle.ourgames.model.Covers;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.PopularViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Covers item);
    }

    private final OnItemClickListener listener;
    private List<Covers> list;

    public PopularAdapter(List list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_cover_popular,parent, false);

        return new PopularViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularViewHolder holder, int position) {
         holder.bind(list.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PopularViewHolder extends RecyclerView.ViewHolder{
        private ImageView coverImgView;
        public PopularViewHolder(@NonNull View itemView) {
            super(itemView);
            coverImgView = itemView.findViewById(R.id.img_cover_popular);

        }

        public void bind(final Covers cover, final OnItemClickListener listener){

            Picasso.get().load("http:"+cover.getUrl()).error(R.drawable.common_google_signin_btn_icon_dark).into(coverImgView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                  Log.d("Picasso err: ", e.getLocalizedMessage());  e.printStackTrace();
                }
            });
            Log.d("ItemView", cover.getUrl());
            itemView.setOnClickListener( v ->{

                listener.onItemClick(cover);
            });
        }
    }
}
