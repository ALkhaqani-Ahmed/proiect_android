package com.example.proiect_android;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LeagueDAO {

    @Insert
    public void insert(League league);

    @Insert
    public void insert(List<League> leagues);

    @Query("Select * from leagues l, matches m where l.matchId=m.id and l.name=:league")
    public List<FavoriteMatches> getAll(String league);

    @Query("Delete from leagues")
    public void deleteAll();

    @Query("Delete from leagues where id = :id1")
    public void deleteWhere(long id1);

    @Delete
    public void deleteL(League league);
}
