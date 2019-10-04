package com.pickle.ourgames.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bosphere.fadingedgelayout.FadingEdgeLayout;
import com.pickle.ourgames.R;
import com.pickle.ourgames.model.ScreenShot;
import com.pickle.ourgames.utility.GradientTransformation;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapterPopularDetail extends SliderViewAdapter<SliderAdapterPopularDetail.SliderAdapterVH> {

    private Context context;
    private List<ScreenShot> screenshotList;

    public SliderAdapterPopularDetail(Context context, List<ScreenShot> screenshotList){
        this.context = context;
        this.screenshotList = screenshotList;
    }


    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_layout_item,null);
        return new SliderAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {

            viewHolder.bind(screenshotList.get(position));

    }

    @Override
    public int getCount() {
        return screenshotList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder{

        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.img_slider);


        }

        public void bind(ScreenShot screen){
            Picasso.get().load("http:" +screen.getUrl()).transform(new GradientTransformation()).fit().into(imageViewBackground, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError(Exception e) {
                    Log.d("Picasso Error: ", e.getMessage());
                    e.printStackTrace();
                }
            });
        }
    }

}
