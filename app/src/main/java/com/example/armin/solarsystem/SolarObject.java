package com.example.armin.solarsystem;

import java.io.Serializable;

/**
 * Created by Armin on 2017-04-05.
 */

public class SolarObject implements Serializable{

    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SolarObject(String name) {
        this.name = name;
    }


}
