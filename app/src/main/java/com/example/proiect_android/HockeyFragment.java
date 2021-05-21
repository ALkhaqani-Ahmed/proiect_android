package com.example.proiect_android;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HockeyFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView hockeyLV;
    List<Match> hockeyMatches;
    MatchAdapter hockeyAdapter;

    public HockeyFragment() {
        // Required empty public constructor
    }

    public static HockeyFragment newInstance(String param1, String param2) {
        HockeyFragment fragment = new HockeyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hockey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        hockeyLV = getView().findViewById(R.id.hockeyLV);
        hockeyMatches = new ArrayList<>();
        Match m = new Match(5,"02:00","l4","Boston","Nashville","7","4");
        Match m1 = new Match(6,"02:00","l4","Edmonton","Montreal","0","0");
        hockeyMatches.add(m);
        hockeyMatches.add(m1);
        hockeyAdapter = new MatchAdapter(getContext(), hockeyMatches);
        hockeyAdapter.notifyDataSetChanged();
        hockeyLV.setAdapter(hockeyAdapter);
        setListViewHeightBasedOnChildren(hockeyLV);

        final MatchDB matchesDB = MatchDB.getInstance(getContext());
        hockeyLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Match m = hockeyMatches.get(i);
                    FavoriteMatches fm = new FavoriteMatches(m.id, m.time, m.league, m.team1, m.team2, m.score1, m.score1);
                    matchesDB.getMatchDao().insert(fm);
                    League l1 = new League(m.league,m.id);
                    matchesDB.getLeagueDao().insert(l1);
                    Toast.makeText(getContext(), "Added to favorite", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getContext(),"Already added to favorite", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });
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
