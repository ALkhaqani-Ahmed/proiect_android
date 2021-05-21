package com.example.proiect_android;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = FavoriteMatches.class,
        parentColumns = "id",
        childColumns = "matchId",
        onDelete = ForeignKey.CASCADE),tableName = "leagues")
public class League {

    @PrimaryKey(autoGenerate = true)
    private int id;
    public String name;
    public int matchId;

    @Ignore
    public League() {
    }

    public League( String name, int matchId) {

        this.name = name;
        this.matchId = matchId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
}
