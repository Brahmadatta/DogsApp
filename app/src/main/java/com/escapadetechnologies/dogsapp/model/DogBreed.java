package com.escapadetechnologies.dogsapp.model;

public class DogBreed {

    public String breedId;

    public String dogBreed;

    public String lifeSpan;

    public String breedGroup;

    public String bredFor;

    public String temparment;

    public String imageUrl;

    public String uId;

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
