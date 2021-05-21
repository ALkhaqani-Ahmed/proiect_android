package com.example.proiect_android;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SportNewsFragment extends Fragment {

    private String title;
    private String link;

    private TextView titleET;
    FrameLayout fr;


    public SportNewsFragment(String _title, String _link) {
       title =  _title;
       link = _link;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sport_news, container, false);

        titleET = view.findViewById(R.id.sportNewsTitle);

        titleET.setText(title);
        fr = view.findViewById(R.id.frameLayout);

        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(link);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return view;
    }

}
