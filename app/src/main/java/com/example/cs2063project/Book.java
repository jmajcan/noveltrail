package com.example.cs2063project;

import java.io.Serializable;
import java.util.Date;

class Book implements Serializable {
    String title;
    String author;
    String pagesRead;
    String pageCount;
    boolean isReading;
    Date startDate;
    Date endDate;
    long id;

    Book(String title, String author, String pagesRead, String pageCount, boolean isReading, Date startDate, Date endDate, long id) {
        this.title = title;
        this.author = author;
        this.pagesRead = pagesRead;
        this.pageCount = pageCount;
        this.isReading = isReading;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = id;
    }
}