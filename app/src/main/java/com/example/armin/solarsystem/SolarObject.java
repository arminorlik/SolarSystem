package com.example.armin.solarsystem;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by Armin on 2017-04-05.
 */

public class SolarObject implements Serializable {

    String name;
    String image;
    String text;

    SolarObject[] moons;
    String video;
    private JSONArray moons1;

    public SolarObject(JSONObject jsonObject) throws JSONException {
        name = jsonObject.getString("name");
        image = String.format("images/%s.jpg", name.toLowerCase());
        text = String.format("texts/&s.jpg", name.toLowerCase());
        video = jsonObject.optString("video");
        moons1 = jsonObject.optJSONArray("moons");
        if (moons1 != null){
            moons = getSolarObjectsFromJsonArray(moons1);
        }


    }

    public SolarObject[] getMoons() {
        return moons;
    }

    public void setMoons(SolarObject[] moons) {
        this.moons = moons;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SolarObject(String name) {
        this.name = name;
    }

    public SolarObject(SolarObject[] moons) {

        this.moons = moons;
    }

    public static SolarObject[] loadArrayFromJson(Context context, String type){

        try {
            String json = loadFromAssest(context, "solar.json");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(type);

            SolarObject[] solarObjects = getSolarObjectsFromJsonArray(jsonArray);

            return getSolarObjectsFromJsonArray(jsonArray);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new SolarObject[0];
    }

    @NonNull
    private static SolarObject[] getSolarObjectsFromJsonArray(JSONArray jsonArray) throws JSONException {
        SolarObject[] solarObjects = new SolarObject[jsonArray.length()];

        for (int i = 0; i < jsonArray.length(); i++) {
            SolarObject solarObject = new SolarObject(jsonArray.getJSONObject(i));

            solarObjects[i] = solarObject;
        }
        return solarObjects;
    }

    public static String loadFromAssest(Context context, String filname) throws IOException {
        InputStream inputStream = context.getAssets().open(filname);
        int size = inputStream.available();
        byte[] buffor = new byte[size];

        inputStream.read(buffor);
        inputStream.close();
        return new String(buffor,"UTF-8");
    }

    public boolean hasMoon() {
        return moons != null && moons.length > 0;
    }
}
