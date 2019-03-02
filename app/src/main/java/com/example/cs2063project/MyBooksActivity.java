package com.example.cs2063project;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.widget.EditText;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyBooksActivity extends AppCompatActivity

            implements NavigationView.OnNavigationItemSelectedListener {

        private List<Book> books;
        private RecyclerView rv;
        private Context context;
        private String titleText = "";
        private String authorText = "";
        private String pageCountText = "";

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

            FileInputStream fileInputStream;
            ObjectInputStream objectInputStream;
            String filename = "bookStorage";
            books = new ArrayList<>();
            try {
                fileInputStream = openFileInput(filename);
                objectInputStream = new ObjectInputStream(fileInputStream);
                books = (List) objectInputStream.readObject();
                objectInputStream.close();
                initializeAdapter();
            } catch (Exception e) {
                e.printStackTrace();
            }
            addBookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder newBookBuilder = new AlertDialog.Builder(MyBooksActivity.this);

                    View newBookDialog = getLayoutInflater().inflate(R.layout.new_book_dialog, null);
                    final EditText inputTitle = (EditText) newBookDialog.findViewById(R.id.input_title);
                    final EditText inputAuthor = (EditText) newBookDialog.findViewById(R.id.input_author);
                    final EditText inputPageCount = (EditText) newBookDialog.findViewById(R.id.input_page_count);

                    inputTitle.setInputType(InputType.TYPE_CLASS_TEXT);
                    inputAuthor.setInputType(InputType.TYPE_CLASS_TEXT);
                    inputPageCount.setInputType(InputType.TYPE_CLASS_TEXT);

                    newBookBuilder.setView(newBookDialog);
                    newBookBuilder.setCancelable(true);
                    // Set up the buttons
                    newBookBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            titleText = inputTitle.getText().toString();
                            authorText = inputAuthor.getText().toString();
                            pageCountText = inputPageCount.getText().toString();
                            books.add(new Book(titleText, authorText, pageCountText));
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
                            initializeAdapter();
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
            MyBooksAdapter adapter = new MyBooksAdapter(books);
            rv.setAdapter(adapter);
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
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

}
