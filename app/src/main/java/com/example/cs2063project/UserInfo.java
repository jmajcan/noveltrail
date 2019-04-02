package com.example.cs2063project;

import java.io.Serializable;

public class UserInfo implements Serializable {
    String email;
    String password;
    String user_fname;
    String user_lname;

    UserInfo(String email, String password, String user_fname, String user_lname){
        this.email = email;
        this.password = password;
        this.user_fname = user_fname;
        this.user_lname = user_lname;
    }
}
