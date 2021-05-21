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
public class BasketballFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ListView basketballLV;
    List<Match> basketballMatches;
    MatchAdapter basketballAdapter;

    public BasketballFragment() {
        // Required empty public constructor
    }

    public static BasketballFragment newInstance(String param1, String param2) {
        BasketballFragment fragment = new BasketballFragment();
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
        return inflater.inflate(R.layout.fragment_basketball, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        basketballLV = getView().findViewById(R.id.basketballLV);
        basketballMatches = new ArrayList<>();
        Match m = new Match(11,"00:30","l1","Charlotte Hornets","Utah Jazz","100","98");
        Match m1 = new Match(12,"FT","l1","Detroit","Toronto","0","4");
        basketballMatches.add(m);
        basketballMatches.add(m1);
        basketballAdapter = new MatchAdapter(getContext(), basketballMatches);
        basketballAdapter.notifyDataSetChanged();
        basketballLV.setAdapter(basketballAdapter);
        setListViewHeightBasedOnChildren(basketballLV);

        final MatchDB matchesDB = MatchDB.getInstance(getContext());
        basketballLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {

                    Match m = basketballMatches.get(i);
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
