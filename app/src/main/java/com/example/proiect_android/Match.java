package com.example.proiect_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Match extends AppCompatActivity {

    private TextView _time;
    private TextView _league;
    private TextView _team1;
    private TextView _team2;
    private TextView _score1;
    private TextView _score2;
    private Button mButton;

    public int id;
    public String time;
    public String league;
    public String team1;
    public String team2;
    public String score1;
    public String score2;

    public Match(int id, String time, String league, String team1, String team2, String score1, String score2){
        this.id = id;
        this.time = time;
        this.league = league;
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;
    }
    public Match(){ }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Bundle bundle = getIntent().getExtras();

        _time = findViewById(R.id.matchTime);
        _league = findViewById(R.id.matchLeague);
        _team1 = findViewById(R.id.matchTeam1);
        _team2 = findViewById(R.id.matchTeam2);
        _score1 = findViewById(R.id.matchScore1);
        _score2 = findViewById(R.id.matchScore2);

        mButton = findViewById(R.id.matchButton);




        if(bundle.getString("time")!= null)
        {
            _time.setText(bundle.getString("time"));
        }
        if(bundle.getString("league")!= null)
        {
            _league.setText(bundle.getString("league"));
        }
        if(bundle.getString("team1")!= null)
        {
            _team1.setText(bundle.getString("team1"));
        }
        if(bundle.getString("team2")!= null)
        {
            _team2.setText(bundle.getString("team2"));
        }
        if(bundle.getString("score1")!= null)
        {
            _score1.setText(bundle.getString("score1"));
        }
        if(bundle.getString("score2")!= null)
        {
            _score2.setText(bundle.getString("score2"));
        }
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MatchDB matchesDB = MatchDB.getInstance(getApplicationContext());
                Toast.makeText(getApplicationContext(), _league.getText(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), LeagueMatches.class);
                intent.putExtra("league", _league.getText());
                startActivity(intent);
            }
        });

    }
}
