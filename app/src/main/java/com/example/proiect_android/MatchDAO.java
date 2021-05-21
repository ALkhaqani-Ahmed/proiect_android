package com.example.proiect_android;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MatchDAO {

    @Insert
    public void insert(FavoriteMatches match);

    @Insert
    public void insert(List<FavoriteMatches> matches);

    @Query("Select * from matches")
    public List<FavoriteMatches> getAll();

    @Query("Delete from matches")
    public void deleteAll();

    @Query("Delete from matches where id = :id1")
    public void deleteWhere(long id1);

    @Delete
    public void deleteM(FavoriteMatches match);

}
