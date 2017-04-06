package com.example.armin.solarsystem;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Armin on 2017-04-06.
 */

public class SolarObjectAdapter extends RecyclerView.Adapter<SolarObjectAdapter.SolarObjectViewHolder> {


    private SolarObject[] solarObjects;

    public SolarObjectAdapter(SolarObject[] solarObjects) {
        this.solarObjects = solarObjects;
    }


    @Override
    public SolarObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solar_object, parent, false);

        return new SolarObjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SolarObjectViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return solarObjects.length;
    }

    class SolarObjectViewHolder extends RecyclerView.ViewHolder{

        public SolarObjectViewHolder(View itemView) {
            super(itemView);
        }
    }
}
