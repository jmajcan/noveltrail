package com.example.cs2063project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;


public class SummaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Book> books;
    private RecyclerView rv;
    private List<Book> currentlyReading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        rv=(RecyclerView)findViewById(R.id.rvView);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);


        books = loadData();
        currentlyReading = new ArrayList<>();

        int size = books.size();

        for(int i = 0; i < books.size(); i++){
            if(books.get(i).isReading){
                currentlyReading.add(books.get(i));
            }
        }

        initializeAdapter();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = null;
        if (id == R.id.nav_my_books) {
            intent = new Intent(this,MyBooksActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            intent = new Intent(this,ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_summary) {
            intent = new Intent(this,SummaryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_data){
            intent = new Intent(this,DataActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initializeAdapter(){
        SummaryAdapter adapter = new SummaryAdapter(currentlyReading, getApplicationContext());
        rv.setAdapter(adapter);
    }


    private List<Book> loadData(){
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;
        String filename = "bookStorage";
        books = new ArrayList<>();
        try {
            fileInputStream = openFileInput(filename);
            objectInputStream = new ObjectInputStream(fileInputStream);
            books = (List) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return books;
    }
}
