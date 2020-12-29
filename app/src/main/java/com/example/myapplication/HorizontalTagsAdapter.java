package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HorizontalTagsAdapter extends RecyclerView.Adapter<HorizontalTagsAdapter.ViewHolder> {

    public Context mContext;
    private List<tags> tagsList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tagName;

        public ViewHolder(View view) {
            super(view);
            tagName = view.findViewById(R.id.textViewTags);

        }

    }

    public HorizontalTagsAdapter(Context mContext, List<tags> tagsList) {
        this.mContext = mContext;
        this.tagsList = tagsList;
    }

    @NonNull
    @Override
    public HorizontalTagsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tags_layout, parent, false);

        return new HorizontalTagsAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalTagsAdapter.ViewHolder holder, int position) {

        final tags tags = tagsList.get(position);

        holder.tagName.setText(tags.getTagName());

    }

    @Override
    public int getItemCount() {
        return tagsList.size();
    }

}
