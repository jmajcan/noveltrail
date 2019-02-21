package com.example.cs2063project;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.List;

public class MyBooksAdapterClass extends RecyclerView.Adapter<MyBooksAdapterClass.myViewHolder> {

    List<Integer> listItem;

    public MyBooksAdapterClass(List<Integer> passedListItem){
        this.listItem = passedListItem;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_my_books, parent, false);

        myViewHolder holder = new myViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        int itemNumber = position+1;
        holder.itemTextView.setText("Item Number " + itemNumber + ": " + listItem.get(position));
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView itemTextView;

        public myViewHolder(View view){
            super(view);
            itemTextView = view.findViewById(R.id.tv_itemTextView);
        }
    }
}