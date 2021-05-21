package com.example.proiect_android;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    ListView matchesLv;
    List<Match> matches;
    MatchAdapter matchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        matchesLv = findViewById(R.id.matchesLV);
        matches = new ArrayList<>();
        final MatchDB matchesDB = MatchDB.getInstance(this);


       for(FavoriteMatches m : matchesDB.getMatchDao().getAll()){
           Match match = new Match(m.getId(), m.getTime(), m.getLeague(), m.getTeam1(), m.getTeam2(), m.getScore1(), m.getScore2());
           matches.add((match));
       }

        matchesAdapter = new MatchAdapter(this, matches);
        matchesAdapter.notifyDataSetChanged();
        matchesLv.setAdapter(matchesAdapter);

        matchesLv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Deleted", Toast.LENGTH_SHORT).show();
                Match m = matches.get(i);
                FavoriteMatches fm = new FavoriteMatches(m.id, m.time, m.league, m.team1, m.team2,m.score1, m.score1);
                matchesDB.getMatchDao().deleteM(fm);

                matches.remove(m);

                matchesAdapter = new MatchAdapter(view.getContext(), matches);
                matchesAdapter.notifyDataSetChanged();
                matchesLv.setAdapter(matchesAdapter);
                setListViewHeightBasedOnChildren(matchesLv);
                return true;
            }
        });

        setListViewHeightBasedOnChildren(matchesLv);
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            if (listItem instanceof ViewGroup)
                listItem.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);

    }

}
