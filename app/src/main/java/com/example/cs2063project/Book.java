package com.example.cs2063project;

import java.io.Serializable;

class Book implements Serializable {
    String title;
    String author;
    String pageCount;
    long id;
   // int photoId;

    Book(String title, String author, String pageCount, long id) {
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        this.id = id;
       // this.photoId = photoId;
    }
}