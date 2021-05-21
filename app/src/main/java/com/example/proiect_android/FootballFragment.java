package com.example.proiect_android;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FootballFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Button butonAfisare;
    private String mParam1;
    private String mParam2;
    EditText editTextData;
    ListView footballLV;
    List<Match> footballMatches;
    MatchAdapter footballAdapter;


    public FootballFragment() {
        // Required empty public constructor
    }

    public static FootballFragment newInstance(String param1, String param2) {
        FootballFragment fragment = new FootballFragment();
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

        return inflater.inflate(R.layout.fragment_football, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        footballLV = getView().findViewById(R.id.footballLV);
        footballMatches = new ArrayList<>();
        editTextData = getView().findViewById(R.id.editTextData);
        butonAfisare  =getView().findViewById(R.id.buttonAfiseazaMeciuri);



        Match m0 = new Match(1,"14:30","England Premier League","Everton","Arsenal","0","0");
        Match m1 = new Match(2,"19:30","England Premier League", "Stroke city","Middlesbrough","1","2");
        Match m2 = new Match(3,"22:20","England Premier League","Eibar","Granada","3","0");
        Match m3 = new Match(4,"23:00","England Premier League","Gimnastic","UE Olot","3","1");


        butonAfisare.setOnClickListener( v-> {
            GetDataService service = MatchesClientInstance.getRetrofitInstance().create(GetDataService.class);

            Call<Results> call = service.getResults(editTextData.getText().toString(),"UEFA");
            call.enqueue(new Callback<Results>() {
                @Override
                public void onResponse(Call<Results> call, Response<Results> response) {
                    Results results = response.body();
                    ArrayAdapter adapter = new ArrayAdapter<>(getContext() , android.R.layout.simple_list_item_1, results.matches);

                    footballLV.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<Results> call, Throwable t) {
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();

                }
            });
        });


//        footballMatches = Arrays.asList(m0, m1, m2, m3);
//        footballAdapter = new MatchAdapter(getContext(), footballMatches);
//        footballAdapter.notifyDataSetChanged();
//        footballLV.setAdapter(footballAdapter);
//        setListViewHeightBasedOnChildren(footballLV);
//        final MatchDB matchesDB = MatchDB.getInstance(getContext());
//        footballLV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                try {
//                    Match m = footballMatches.get(i);
//                    FavoriteMatches fm = new FavoriteMatches(m.id,m.time, m.league, m.team1, m.team2,m.score1, m.score1);
//                    matchesDB.getMatchDao().insert(fm);
//                    League l1 = new League(m.league,m.id);
//                    matchesDB.getLeagueDao().insert(l1);
//                    Toast.makeText(getContext(),"Added to favorite", Toast.LENGTH_LONG).show();
//
//                }catch (Exception e){
//                    Toast.makeText(getContext(),"Already added to favorite", Toast.LENGTH_LONG).show();
//                    System.out.println(e.getMessage());
//                }
//                return true;
//            }
//        });


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
