package com.example.proiect_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LeagueMatches extends AppCompatActivity {

    ListView lv;
    Button button;
    String league;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_matches);
        Bundle bundle = getIntent().getExtras();

        button = findViewById(R.id.leagueMatchesButton);

        if(bundle.getString("league")!= null)
        {
            league = (bundle.getString("league"));
        }

        lv = findViewById(R.id.leagueMatchesLV);


        final MatchDB matchesDB = MatchDB.getInstance(this);


        ArrayAdapter<FavoriteMatches> adaptor1 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, matchesDB.getLeagueDao().getAll(league));
        lv.setAdapter(adaptor1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ExportDatabaseCSVTask().execute();
            }
        });
    }




    class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {

        private MatchDB database;

        @Override
        protected void onPreExecute() {

            database = MatchDB.getInstance(LeagueMatches.this);
        }

        protected Boolean doInBackground(final String... args) {



            File file = new File(Environment.getExternalStorageDirectory(), league+".csv");

            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));

                List<FavoriteMatches> list = database.getLeagueDao().getAll(league);

                    for(int i=0; i<list.size(); i++){
                        String[] stringArray ={String.valueOf(list.get(i).getTime()), String.valueOf(list.get(i).getTeam1()), String.valueOf(list.get(i).getTeam2())};
                        csvWrite.writeNext(stringArray);
                    }

                csvWrite.close();

                return true;
            } catch (IOException e) {
                Log.e("CSV",e.getMessage());
                return false;
            }


        }

        protected void onPostExecute(final Boolean success) {

            if (success) {
                Toast.makeText(LeagueMatches.this, "Export successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(LeagueMatches.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    class CSVWriter {
        private PrintWriter pw;
        private char separator;
        private char escapechar;
        private String lineEnd;
        private char quotechar;
        public static final char DEFAULT_SEPARATOR = ',';
        public static final char NO_QUOTE_CHARACTER = '\u0000';
        public static final char NO_ESCAPE_CHARACTER = '\u0000';
        public static final String DEFAULT_LINE_END = "\n";
        public static final char DEFAULT_QUOTE_CHARACTER = '"';
        public static final char DEFAULT_ESCAPE_CHARACTER = '"';
        public CSVWriter(Writer writer) {
            this(writer, DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER,
                    DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END);
        }

        public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
            this.pw = new PrintWriter(writer);
            this.separator = separator;
            this.quotechar = quotechar;
            this.escapechar = escapechar;
            this.lineEnd = lineEnd;
        }
        public void writeNext(String[] nextLine) {
            if (nextLine == null)
                return;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nextLine.length; i++) {

                if (i != 0) {
                    sb.append(separator);
                }
                String nextElement = nextLine[i];
                if (nextElement == null)
                    continue;
                if (quotechar != NO_QUOTE_CHARACTER)
                    sb.append(quotechar);
                for (int j = 0; j < nextElement.length(); j++) {
                    char nextChar = nextElement.charAt(j);
                    if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
                        sb.append(escapechar).append(nextChar);
                    } else if (escapechar != NO_ESCAPE_CHARACTER && nextChar == escapechar) {
                        sb.append(escapechar).append(nextChar);
                    } else {
                        sb.append(nextChar);
                    }
                }
                if (quotechar != NO_QUOTE_CHARACTER)
                    sb.append(quotechar);
            }
            sb.append(lineEnd);
            pw.write(sb.toString());
        }
        public void close() throws IOException {
            pw.flush();
            pw.close();
        }
        public void flush() throws IOException {
            pw.flush();
        }
    }


}
