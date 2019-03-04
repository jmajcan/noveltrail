package com.example.cs2063project;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksAdapter.BookViewHolder> {

    public class BookViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView bookTitle;
        TextView bookAuthor;
        TextView bookPageCount;
        Button deleteBookButton;

        public BookViewHolder(View v) {
            super(v);
            cv = (CardView)v.findViewById(R.id.cv);
            bookTitle = (TextView)v.findViewById(R.id.book_title);
            bookAuthor = (TextView)v.findViewById(R.id.book_author);
            bookPageCount = (TextView)v.findViewById(R.id.book_page_count);
            deleteBookButton = (Button)v.findViewById(R.id.delete_book);
            deleteBookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    removeAt(getAdapterPosition());
                                    Toast.makeText(v.getContext(),"Book Deleted.",Toast.LENGTH_SHORT).show();
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };
                    context = v.getContext();
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to delete " + bookTitle.getText() + "?")
                            .setNegativeButton("No", dialogClickListener)
                            .setPositiveButton("Yes", dialogClickListener).show();
                }
            });

            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    List<Book> books;
    Context context;

    MyBooksAdapter(List<Book> books, Context context){
        this.books = books;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView rv) {
        super.onAttachedToRecyclerView(rv);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        BookViewHolder bvh = new BookViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(BookViewHolder bookViewHolder, int i) {
        bookViewHolder.bookTitle.setText(books.get(i).title);
        bookViewHolder.bookAuthor.setText(books.get(i).author);
        bookViewHolder.bookPageCount.setText(books.get(i).pageCount);
    }

    public void removeAt(int position) {
        books.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, books.size());
        String filename = "bookStorage";
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        try {
            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(books);
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}