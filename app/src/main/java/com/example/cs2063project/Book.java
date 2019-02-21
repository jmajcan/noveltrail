package com.example.cs2063project;

import java.io.Serializable;

class Book implements Serializable {
    String title;
    String author;
    String pageCount;
   // int photoId;

    Book(String title, String author, String pageCount) {
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
       // this.photoId = photoId;
    }
}