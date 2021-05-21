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
public class TenisFragment extends Fragment {



    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    ListView tennisLV;
    List<Match> tennisMatches;
    MatchAdapter tennisAdapter;

    public TenisFragment() {
        // Required empty public constructor
    }

    public static TenisFragment newInstance(String param1, String param2) {
        TenisFragment fragment = new TenisFragment();
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
        return inflater.inflate(R.layout.fragment_tenis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tennisLV = getView().findViewById(R.id.tennisLV);
        tennisMatches = new ArrayList<>();
        Match m = new Match(7,"9:20","l5","t1","t2","1","2");
        Match m1 = new Match(8,"59:20","l5","t2","t3","2","3");
        tennisMatches.add(m);
        tennisMatches.add(m1);
        tennisAdapter = new MatchAdapter(getContext(), tennisMatches);
        tennisAdapter.notifyDataSetChanged();
        tennisLV.setAdapter(tennisAdapter);
        setListViewHeightBasedOnChildren(tennisLV);

        final MatchDB matchesDB = MatchDB.getInstance(getContext());
        tennisLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {

                    Match m = tennisMatches.get(i);
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
