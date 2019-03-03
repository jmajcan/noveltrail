package com.example.cs2063project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URI;

public class ProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        if (id == R.id.nav_welcome) {
            // Handle the camera action
            intent = new Intent(this,WelcomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_my_books) {
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

    public void showImageGallery(View view){
        ImageView profileImage = (ImageView) findViewById(R.id.profile_image);
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult (requestCode , resultCode , intent );
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            Uri imageUri = intent.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ImageView imageView = (ImageView)findViewById(R.id.profile_image);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(this, "Profile Image Changed", Toast.LENGTH_SHORT).show();

        }
    }

    public void editProfile(View view) {
        TextView name = (TextView) findViewById((R.id.name));
        TextView email = (TextView) findViewById((R.id.email));

        AlertDialog.Builder editProfile = new AlertDialog.Builder(ProfileActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.profile_dialog, null);
        editProfile.setView(dialogView);
        editProfile.setMessage("Edit Profile");
        editProfile.setCancelable(true);
        final EditText nameText = (EditText) dialogView.findViewById((R.id.name));
        final EditText emailText = (EditText) dialogView.findViewById((R.id.email));

        editProfile.setPositiveButton(
                "Save Changes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = nameText.getText().toString();
                        String email = emailText.getText().toString();
                        TextView nameView = findViewById(R.id.profile_name);
                        TextView emailView = findViewById(R.id.profile_email);
                        nameView.setText("Name: " + name);
                        emailView.setText("Email: " + email);

                    }
                });

        editProfile.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog profileAlert = editProfile.create();
        profileAlert.show();


    }
}
