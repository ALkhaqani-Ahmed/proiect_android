package com.example.proiect_android;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMatches.class, League.class}, version = 2, exportSchema = false)
public abstract class MatchDB extends RoomDatabase {

    public final static String DB_NAME = "database.db";
    private static MatchDB instanta;

    public static MatchDB getInstance(Context context) {
        if (instanta == null) {
            instanta = Room.databaseBuilder(context, MatchDB.class, DB_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instanta;
    }

    public abstract LeagueDAO getLeagueDao();
    public abstract MatchDAO getMatchDao();

}
