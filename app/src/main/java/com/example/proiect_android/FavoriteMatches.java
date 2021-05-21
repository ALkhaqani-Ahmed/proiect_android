package com.example.proiect_android;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "matches")
public class FavoriteMatches {

    @Ignore
    private String uid;

    @PrimaryKey(autoGenerate = true)
    private int id;
    public String time;
    public String league;
    public String team1;
    public String team2;
    public String score1;
    public String score2;

    public FavoriteMatches(int id, String time, String league, String team1, String team2, String score1, String score2) {
        this.id = id;
        this.time = time;
        this.league = league;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
    }

    @Ignore
    public FavoriteMatches() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getScore1() {
        return score1;
    }

    public void setScore1(String score1) {
        this.score1 = score1;
    }

    public String getScore2() {
        return score2;
    }

    public void setScore2(String score2) {
        this.score2 = score2;
    }

    @Override
    public String toString() {
        return
               time + '\n' + team1 + '\n' + team2;
    }
}
