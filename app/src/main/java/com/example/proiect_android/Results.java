package com.example.proiect_android;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {
    @SerializedName("data")
    List<MatchAPI> matches;

    public Results(List<MatchAPI> matches) {
        this.matches = matches;
    }

    public List<MatchAPI> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchAPI> matches) {
        this.matches = matches;
    }

    @Override
    public String toString() {
        return matches.toString();
    }
}

class MatchAPI {
    @SerializedName("home_team")
    String home_team;
    @SerializedName("away_team")
    String away_team;
    @SerializedName("result")
    String result;

    public MatchAPI(String home_team, String away_team, String result) {
        this.home_team = home_team;
        this.away_team = away_team;
        this.result = result;
    }

    public String getHome_team() {
        return home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    @Override
    public String toString() {
        return home_team + " " + result + " " + away_team ;
    }
}

