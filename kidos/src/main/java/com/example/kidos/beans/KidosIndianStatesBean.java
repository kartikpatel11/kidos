package com.example.kidos.beans;

import java.util.List;

/**
 * Created by Kartik on 24/11/17.
 */

public class KidosIndianStatesBean {

    private String state;
    private List<KidosIndianCitiesBean> cities;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<KidosIndianCitiesBean> getCities() {
        return cities;
    }

    public void setCity(List<KidosIndianCitiesBean> city) {
        this.cities = city;
    }

    @Override
    public String toString() {
        return "KidosPartnersIndianStatesBean{" +
                "state='" + state + '\'' +
                ", city=" + cities +
                '}';
    }
}


