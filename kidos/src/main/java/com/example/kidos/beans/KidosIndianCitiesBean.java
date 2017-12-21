package com.example.kidos.beans;

import java.util.Arrays;

/**
 * Created by Kartik on 24/11/17.
 */

public class KidosIndianCitiesBean {
    private String city;
    private String[] areas;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String[] getAreas() {
        return areas;
    }

    public void setAreas(String[] areas) {
        this.areas = areas;
    }

    @Override
    public String toString() {
        return "KidosPartnersIndianCitiesBean{" +
                "city='" + city + '\'' +
                ", areas=" + Arrays.toString(areas) +
                '}';
    }
}
