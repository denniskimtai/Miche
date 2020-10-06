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

public class PickCropListAdapter extends RecyclerView.Adapter<PickCropListAdapter.ViewHolder> {

    public Context mContext;
    private List<PickCropData> cropsList;
    private LinearLayout crop_item_layout;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView cropName;
        public ImageView cropImage;

        public ViewHolder(View view) {
            super(view);
            cropName = view.findViewById(R.id.cropName);
            cropImage =  view.findViewById(R.id.cropImage);
            crop_item_layout = view.findViewById(R.id.crop_item_layout);

        }

    }

    public PickCropListAdapter(Context mContext, List<PickCropData> cropsList) {
        this.mContext = mContext;
        this.cropsList = cropsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crop_item_layout, parent, false);

        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final PickCropData pickCropData = cropsList.get(position);

        holder.cropName.setText(pickCropData.getCropName());
        //load image using glide
        Glide.with(mContext).load(pickCropData.getCropImage()).into(holder.cropImage);

        //onclick listener for one item in the recycler view
        crop_item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, CropDetailsActivity.class);
                mContext.startActivity(intent);

            }
        });

    }


    @Override
    public int getItemCount() {
        return cropsList.size();
    }

}
