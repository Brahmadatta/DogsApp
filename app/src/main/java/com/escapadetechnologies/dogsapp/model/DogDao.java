package com.escapadetechnologies.dogsapp.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DogDao {

    @Insert
    List<Long> insertAll(DogBreed... dogs);


    @Query("SELECT * FROM dogbreed")
    List<DogBreed> getAllDOgs();

    @Query("SELECT * FROM dogbreed WHERE uuid = :dogId")
    DogBreed getDog(int dogId);

    @Query("DELETE FROM DogBreed")
    void deleteAllDogs();

    
}
