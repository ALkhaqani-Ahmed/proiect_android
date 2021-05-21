package com.example.proiect_android;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;

public class MatchAdapter extends BaseAdapter{

    private LayoutInflater thisInflater;
    private List<Match> matches;

    public MatchAdapter(Context con, List<Match> list){
        this.thisInflater = LayoutInflater.from(con);
        matches = (list);
    }

    @Override
    public int getCount() {
        return matches.size();
    }

    @Override
    public Object getItem(int position) {
        return matches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return matches.get(position).hashCode();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = thisInflater.inflate(R.layout.fragment_match, parent, false);

            TextView time = convertView.findViewById(R.id.time);
            TextView team1 = convertView.findViewById(R.id.team1);
            TextView team2 = convertView.findViewById(R.id.team2);
            LinearLayout ll = convertView.findViewById(R.id.matchll);


            time.setText(matches.get(position).time);
            team1.setText(matches.get(position).team1);
            team2.setText(matches.get(position).team2);


            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(thisInflater.getContext(),Match.class);
                    intent.putExtra("time", matches.get(position).time);
                    intent.putExtra("league", matches.get(position).league);
                    intent.putExtra("team1", matches.get(position).team1);
                    intent.putExtra("team2", matches.get(position).team2);
                    intent.putExtra("score1", matches.get(position).score1);
                    intent.putExtra("score2", matches.get(position).score2);

                    thisInflater.getContext().startActivity(intent);
                }
            });

        }

        return convertView;
    }


}
