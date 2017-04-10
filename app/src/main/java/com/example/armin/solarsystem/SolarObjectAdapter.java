package com.example.armin.solarsystem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Armin on 2017-04-06.
 */

public class SolarObjectAdapter extends RecyclerView.Adapter<SolarObjectAdapter.SolarObjectViewHolder> {

    private SolarObject[] solarObjects;
    private SolarObjectClickedListener solarObjectClickedListener;


    public SolarObjectAdapter(SolarObject[] solarObjects) {
        this.solarObjects = solarObjects;
    }



    public void setSolarObjectClickedListener(SolarObjectClickedListener solarObjectClickedListener) {
        this.solarObjectClickedListener = solarObjectClickedListener;
    }



    @Override
    public SolarObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solar_object, parent, false);

        return new SolarObjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SolarObjectViewHolder holder, int position) {
        SolarObject solarObject = solarObjects[position];
        holder.setSolarObject(solarObject);
    }

    @Override
    public int getItemCount() {
        return solarObjects.length;
    }

    private void itemClicked(SolarObject solarObject) {
        if (solarObjectClickedListener != null){
            solarObjectClickedListener.SolarObjectClicked(solarObject);
        }


    }

    class SolarObjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.itemImage)
        ImageView itemImage;
        @BindView(R.id.itemText)
        TextView itemText;

        private SolarObject solarObject;

        public SolarObjectViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void setSolarObject(SolarObject solarObject) {
            this.solarObject = solarObject;

            itemText.setText(solarObject.getName());
            Glide.with(itemImage.getContext())
                    .load(solarObject.getImagePath())
                    .placeholder(R.drawable.planet_placeholder)
                    .fitCenter()
                    .into(itemImage);
        }

        public SolarObject getSolarObject() {
            return solarObject;
        }

        @Override
        public void onClick(View view) {
            itemClicked(solarObject);
        }
    }

    public interface SolarObjectClickedListener {
        void SolarObjectClicked(SolarObject solarObject);
    }
}
