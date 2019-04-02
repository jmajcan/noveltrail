package com.example.cs2063project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyBooksActivity extends AppCompatActivity

            implements NavigationView.OnNavigationItemSelectedListener {

        private List<Book> books;
        private RecyclerView rv;
        private String titleText = "";
        private String authorText = "";
        private String pageCountText = "";
        private Date startDate;
        private Date endDate;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_my_books);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            rv=(RecyclerView)findViewById(R.id.rv);

            LinearLayoutManager llm = new LinearLayoutManager(this);
            rv.setLayoutManager(llm);
            rv.setHasFixedSize(true);

            Button addBookButton = (Button) findViewById(R.id.add_book);

            books = loadData();

            addBookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    AlertDialog.Builder newBookBuilder = new AlertDialog.Builder(MyBooksActivity.this);

                    View newBookDialog = getLayoutInflater().inflate(R.layout.new_book_dialog, null);
                    final EditText inputTitle = (EditText) newBookDialog.findViewById(R.id.input_title);
                    final EditText inputAuthor = (EditText) newBookDialog.findViewById(R.id.input_author);
                    final EditText inputPageCount = (EditText) newBookDialog.findViewById(R.id.input_page_count);
                    final CheckBox reading = (CheckBox) newBookDialog.findViewById(R.id.checkBox);
                    final EditText inputStartDate = (EditText) newBookDialog.findViewById(R.id.input_start_date);
                    final EditText inputEndDate = (EditText) newBookDialog.findViewById(R.id.input_end_date);

                    inputTitle.setInputType(InputType.TYPE_CLASS_TEXT);
                    inputAuthor.setInputType(InputType.TYPE_CLASS_TEXT);
                    inputPageCount.setInputType(InputType.TYPE_CLASS_TEXT);
                    inputStartDate.setInputType(InputType.TYPE_CLASS_DATETIME);
                    inputEndDate.setInputType(InputType.TYPE_CLASS_DATETIME);

                    newBookBuilder.setView(newBookDialog);
                    newBookBuilder.setCancelable(true);
                    // Set up the buttons
                    newBookBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            titleText = inputTitle.getText().toString();
                            authorText = inputAuthor.getText().toString();
                            pageCountText = inputPageCount.getText().toString();
                            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                            try {
                                startDate = (Date)formatter.parse(inputStartDate.getText().toString());
                                endDate = (Date)formatter.parse(inputEndDate.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            if(books.size() > 0){
                                books.add(new Book(titleText, authorText, "0",pageCountText, reading.isChecked(), startDate, endDate, books.get(books.size()-1).id+1));
                            }else{
                                books.add(new Book(titleText, authorText, "0", pageCountText, reading.isChecked(), startDate, endDate, 0));
                            }

                            saveData((ArrayList<Book>)books);

                            try{
                                rv.getAdapter().notifyItemInserted(books.size());
                                rv.getAdapter().notifyItemRangeChanged(0, books.size());
                                Toast.makeText(getApplicationContext(),"Book Added.",Toast.LENGTH_SHORT).show();
                            }catch(NullPointerException e){
                                Toast.makeText(getApplicationContext(),"Null pointer exception.",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    newBookBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog newBookAlert = newBookBuilder.create();
                    newBookAlert.show();
                }
            });

        }
        private void initializeAdapter(){
            MyBooksAdapter adapter = new MyBooksAdapter(books, getApplicationContext());
            rv.setAdapter(adapter);
        }

        private void saveData(ArrayList<Book> books){
            String filename = "bookStorage";
            FileOutputStream fileOutputStream;
            ObjectOutputStream objectOutputStream;

            try {
                fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(books);
                objectOutputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private List<Book> loadData(){
            FileInputStream fileInputStream;
            ObjectInputStream objectInputStream;
            String filename = "bookStorage";
            books = new ArrayList<>();
            initializeAdapter();
            try {
                fileInputStream = openFileInput(filename);
                objectInputStream = new ObjectInputStream(fileInputStream);
                books = (List) objectInputStream.readObject();
                objectInputStream.close();
                initializeAdapter();
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

            Intent intent;
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

}
