package com.example.proiect_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.net.URL;
import java.nio.channels.AsynchronousByteChannel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigation;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_football:
                                openFragment(FootballFragment.newInstance("", ""));
                                return true;
                            case R.id.menu_basketball:
                                openFragment(BasketballFragment.newInstance("", ""));
                                return true;
                            case R.id.menu_cricket:
                                openFragment(CricketFragment.newInstance("", ""));
                                return true;
                            case R.id.menu_hockey:
                                openFragment(HockeyFragment.newInstance("", ""));
                                return true;
                            case R.id.menu_tenis:
                                openFragment(TenisFragment.newInstance("", ""));
                                return true;

                        }
                        return false;
                    }
                });


        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setTitle("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        progressDialog.setCancelable(false);

        GetNews();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.Favorite:
                Intent intent = new Intent(this,FavoriteActivity.class);
                startActivity(intent);
                break;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.ll, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void GetNews() {
        AsyncXML o = new AsyncXML() {
            @Override
            protected void onPostExecute(List<SportNews> sportNews) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.ll,new Home());
                for (SportNews n : sportNews) {
                    fragmentTransaction.add(R.id.ll, new SportNewsFragment(n.getTitle(), n.getLink()));
                }
                fragmentTransaction.commitAllowingStateLoss();
                progressDialog.dismiss();
            }
        };
        try {
            o.execute(new URL("https://www.reddit.com/r/sports/.rss?format=xml?"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
