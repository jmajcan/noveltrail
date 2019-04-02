package com.example.cs2063project;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    Button loginBttn, regBttn;
    EditText username, password;
    private List<UserInfo> userInfo;

    ObjectInputStream objectInputStream;
    String filename = "userInfo";
    String fnameInfo, lnameInfo, usernameInfo, passwordInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        loginBttn = (Button)findViewById(R.id.loginbutton);
        regBttn = (Button)findViewById(R.id.registerButton);
        username = (EditText)findViewById(R.id.loginemail);
        password = (EditText)findViewById(R.id.loginpassword);
        userInfo = new ArrayList<>();
        regBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder registerUser = new AlertDialog.Builder(WelcomeActivity.this);
                View newUserDialog = getLayoutInflater().inflate(R.layout.new_user_dialog, null);

                final EditText inputFirst = (EditText) newUserDialog.findViewById(R.id.fnameIn);
                final EditText inputLast = (EditText) newUserDialog.findViewById(R.id.lnameIn);
                final EditText inputEmail = (EditText) newUserDialog.findViewById(R.id.emailIn);
                final EditText inputPass = (EditText) newUserDialog.findViewById(R.id.passIn);

                inputFirst.setInputType(InputType.TYPE_CLASS_TEXT);
                inputLast.setInputType(InputType.TYPE_CLASS_TEXT);
                inputEmail.setInputType(InputType.TYPE_CLASS_TEXT);
                inputPass.setInputType(InputType.TYPE_CLASS_TEXT);

                registerUser.setView(newUserDialog);
                registerUser.setCancelable(true);

                registerUser.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fnameInfo = inputFirst.getText().toString();
                        lnameInfo = inputLast.getText().toString();
                        usernameInfo = inputEmail.getText().toString();
                        passwordInfo = inputPass.getText().toString();
                        userInfo.add(new UserInfo(usernameInfo, passwordInfo, fnameInfo, lnameInfo));
                        String filename = "userInfo";

                        FileOutputStream fileOutputStream;
                        ObjectOutputStream objectOutputStream;

                        try {
                            fileOutputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                            objectOutputStream = new ObjectOutputStream(fileOutputStream);
                            objectOutputStream.writeObject(userInfo);
                            objectOutputStream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                registerUser.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog newUserAlert = registerUser.create();
                newUserAlert.show();
            }
        });
        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    FileInputStream inputStream = openFileInput(filename);
                    objectInputStream = new ObjectInputStream(inputStream);
                    userInfo = (List) objectInputStream.readObject();

                    //usernameInfo;
                    //passwordInfo;

                    for(int i = 0; i < userInfo.size(); i++){
                        System.out.println(userInfo.get(i).email);
                        System.out.println(userInfo.get(i).password);

                        if(username.getText().toString().equals(userInfo.get(i).email) &&
                                password.getText().toString().equals(userInfo.get(i).password)) {
                            Toast.makeText(getApplicationContext(),
                                    "Redirecting...",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(v.getContext(), SummaryActivity.class);
                            startActivity(intent);
                        } else{
                            Toast.makeText(getApplicationContext(), "Wrong Credentials",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    System.out.println(userInfo);
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
