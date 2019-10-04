package com.escapadetechnologies.dogsapp.model;

import com.google.gson.annotations.SerializedName;

public class DogBreed {

    @SerializedName("id")
    public String breedId;

    @SerializedName("name")
    public String dogBreed;

    @SerializedName("life_span")
    public String lifeSpan;

    @SerializedName("breed_group")
    public String breedGroup;

    @SerializedName("bred_for")
    public String bredFor;

    @SerializedName("temperament")
    public String temparment;

    @SerializedName("url")
    public String imageUrl;

    public String uuid;

    public DogBreed(String breedId, String dogBreed, String lifeSpan, String breedGroup, String bredFor, String temparment, String imageUrl) {
        this.breedId = breedId;
        this.dogBreed = dogBreed;
        this.lifeSpan = lifeSpan;
        this.breedGroup = breedGroup;
        this.bredFor = bredFor;
        this.temparment = temparment;
        this.imageUrl = imageUrl;
    }
}
