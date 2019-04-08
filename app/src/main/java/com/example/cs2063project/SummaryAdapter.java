package com.example.cs2063project;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.BookViewHolder> {

    public class BookViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookPageCount;
        TextView bookEndDate;

        public BookViewHolder(View v) {
            super(v);
            cv = (CardView)v.findViewById(R.id.cv);
            bookTitle = (TextView)v.findViewById(R.id.book_title);
            bookAuthor = (TextView)v.findViewById(R.id.book_author);
            bookPageCount = (TextView)v.findViewById(R.id.book_page_count);
        }
    }

    List<Book> books;
    Context context;

    SummaryAdapter(List<Book> books, Context context){
        this.books = books;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_summary, viewGroup, false);
        BookViewHolder bvh = new BookViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(BookViewHolder bookViewHolder, int i) {
        bookViewHolder.bookTitle.setText(books.get(i).title);
        bookViewHolder.bookAuthor.setText(books.get(i).author);
        bookViewHolder.bookPageCount.setText(books.get(i).pagesRead + "/" + books.get(i).pageCount);
        CharSequence endDate = DateFormat.format("EEE MMM dd", books.get(i).endDate);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}