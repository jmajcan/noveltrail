package com.example.cs2063project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataActivity extends AppCompatActivity

        implements NavigationView.OnNavigationItemSelectedListener {


    private List<Book> books = new ArrayList<>();
    private ArrayList<String> xAxisLabels = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        books = loadData();

        if(!books.isEmpty()) {
            BarChart chart = (BarChart) findViewById(R.id.bar_chart);
            BarData data = new BarData(chartBooks());

            data.setBarWidth(0.9f);
            chart.setData(data);
            chart.getDescription().setEnabled(false);

            final XAxis xAxis = chart.getXAxis();
            xAxisLabels = getXAxisLabels();
            String[] labels = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",                                    "Dec", "Jan"};
            xAxis.setValueFormatter(new LabelFormatter(labels));
            xAxis.setDrawLabels(true);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

            YAxis leftAxis = chart.getAxisLeft();

            chart.setFitBars(true);
            chart.animateY(3000);
        }

    }

    public BarDataSet chartBooks(){
        ArrayList<BarEntry> barEntry = new ArrayList<>() ;

        int janCount = 0;
        int febCount = 0;
        int marCount = 0;
        int apCount = 0;
        int mayCount = 0;
        int junCount = 0;
        int julCount = 0;
        int augCount = 0;
        int sepCount = 0;
        int octCount = 0;
        int novCount = 0;
        int decCount = 0;

        books = loadData();
        for (Book book: books) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(book.endDate);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            if(year == Calendar.getInstance().get(Calendar.YEAR) % 100) {

                if (month == 0) {
                    janCount++;
                    barEntry.add(new BarEntry(0, janCount));
                } else if (month == 1) {
                    febCount++;
                    barEntry.add(new BarEntry(1, febCount));
                } else if (month == 2) {
                    marCount++;
                    barEntry.add(new BarEntry(2, marCount));
                } else if (month == 3) {
                    apCount++;
                    barEntry.add(new BarEntry(3, apCount));
                } else if (month == 4) {
                    mayCount++;
                    barEntry.add(new BarEntry(4, mayCount));
                } else if (month == 5) {
                    junCount++;
                    barEntry.add(new BarEntry(5, junCount));
                } else if (month == 6) {
                    julCount++;
                    barEntry.add(new BarEntry(6, julCount));
                } else if (month == 7) {
                    augCount++;
                    barEntry.add(new BarEntry(7, augCount));
                } else if (month == 8) {
                    sepCount++;
                    barEntry.add(new BarEntry(8, sepCount));
                } else if (month == 9) {
                    octCount++;
                    barEntry.add(new BarEntry(9, octCount));
                } else if (month == 10) {
                    novCount++;
                    barEntry.add(new BarEntry(10, novCount));
                } else if (month == 11) {
                    decCount++;
                    barEntry.add(new BarEntry(11, decCount));
                }
            }

        }
        ArrayList <BarDataSet> dataSets = null;
        BarDataSet barDataSet = new BarDataSet(barEntry, "Amount of Books Read");

        return barDataSet;
    }

    private ArrayList<String> getXAxisLabels () {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Jan");
        xAxis.add("Feb");

        return xAxis;
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

    public class LabelFormatter implements IAxisValueFormatter {
        private final String[] mLabels;

        public LabelFormatter(String[] labels) {
            mLabels = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mLabels[(int) value];
        }

        @Override
        public int getDecimalDigits() { return 1; }
    }

}


