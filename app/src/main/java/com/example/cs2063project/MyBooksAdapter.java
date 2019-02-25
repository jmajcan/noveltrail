package com.example.cs2063project;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.BookViewHolder> {

    public static class BookViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookPageCount;

        BookViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            bookTitle = (TextView)itemView.findViewById(R.id.book_title);
            bookAuthor = (TextView)itemView.findViewById(R.id.book_author);
            bookPageCount = (TextView)itemView.findViewById(R.id.book_page_count);
        }
    }

    List<Book> books;

    MyBooksAdapter(List<Book> books){
        this.books = books;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        BookViewHolder bvh = new BookViewHolder(v);
        Button button = (Button) viewGroup.findViewById(R.id.delete_book);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return bvh;
    }

    @Override
    public void onBindViewHolder(BookViewHolder bookViewHolder, int i) {
        bookViewHolder.bookTitle.setText(books.get(i).title);
        bookViewHolder.bookAuthor.setText(books.get(i).author);
        bookViewHolder.bookPageCount.setText(books.get(i).pageCount);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}