package com.mc2022.template;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> newsArrayList;
    public  NewsAdapter(ArrayList<News> list) {
        newsArrayList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        News news = newsArrayList.get(position);
        holder.item_id.setText(String.valueOf(news.getNumber()));
        // holder.employee_name.setText(employee.getName());
        // name = employee.getName();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                // activity.getSupportFragmentManager().beginTransaction().replace(R.id.main, new BlankFragment2()).addToBackStack(null).commit();

                Bundle args = new Bundle();
                args.putString("title", news.getTitle());
                args.putString("body", news.getBody());
                args.putString("img", news.getImageURL());
                BlankFragment2 fragobj = new BlankFragment2();
                fragobj.setArguments(args);
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main, fragobj);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });





    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView item_id;
        public ViewHolder(View itemView)
        {
            super(itemView);
            item_id = itemView.findViewById(R.id.item_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i("clicked: ", "" + newsArrayList.get(getAdapterPosition()).getTitle());
            Toast.makeText(itemView.getContext(), "Clicked", Toast.LENGTH_SHORT).show();

        }
    }
}

