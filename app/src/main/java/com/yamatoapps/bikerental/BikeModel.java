package com.yamatoapps.bikerental;

public class BikeModel {
    public String id = "";
    public String model = "";
    public String description = "";
    public String age_range = "";

    public String height_range = "";

    public String rate = "";
    public String image_url = "";


    public BikeModel(String model, String description, String age_range, String height_range, String rate, String image_url,String id) {
        this.model = model;
        this.description = description;
        this.age_range = age_range;
        this.height_range = height_range;
        this.rate = rate;
        this.image_url = image_url;
        this.id  = id;
    }
}
