package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    public Context mContext;
    private List<NewsData> newsDataList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView newsTitle;
        public TextView newsLink;
        public ImageView newsImage;

        public ViewHolder(View view) {
            super(view);
            newsTitle = view.findViewById(R.id.news_title);
            newsLink =  view.findViewById(R.id.news_link);
            newsImage = view.findViewById(R.id.news_image);

        }

    }

    public NewsAdapter(Context mContext, List<NewsData> newsDataList) {
        this.mContext = mContext;
        this.newsDataList = newsDataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item_layout, parent, false);

        return new NewsAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final NewsData newsData = newsDataList.get(position);

        holder.newsTitle.setText(newsData.getNewsName());
        holder.newsLink.setText(newsData.getNewsLink());
        //load image using glide
        Glide.with(mContext).load(newsData.getNewsImage()).into(holder.newsImage);


    }

    @Override
    public int getItemCount() {
        return newsDataList.size();
    }



}
