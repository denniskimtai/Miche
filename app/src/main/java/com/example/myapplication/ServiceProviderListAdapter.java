package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ServiceProviderListAdapter extends RecyclerView.Adapter<ServiceProviderListAdapter.ViewHolder> {

    public Context mContext;
    private List<ServiceProviderData> serviceProviderDataList;


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView serviceProviderName;
        public TextView serviceProviderLocation;
        public TextView serviceProviderTags;
        public ImageView serviceProviderImage;
        public CheckBox radioButton;
        public LinearLayout cardLayout;

        public ViewHolder(View view) {
            super(view);
            serviceProviderName = view.findViewById(R.id.serviceProviderName);
            serviceProviderLocation =  view.findViewById(R.id.serviceProviderLocation);
            serviceProviderTags = view.findViewById(R.id.serviceProviderTags);
            serviceProviderImage = view.findViewById(R.id.serviceProviderImage);
            radioButton = view.findViewById(R.id.radio_button);
            cardLayout = view.findViewById(R.id.card_layout);

        }

    }

    public ServiceProviderListAdapter(Context mContext, List<ServiceProviderData> serviceProviderDataList) {
        this.mContext = mContext;
        this.serviceProviderDataList = serviceProviderDataList;
    }


    @NonNull
    @Override
    public ServiceProviderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_provider_list_item_layout, parent, false);

        return new ServiceProviderListAdapter.ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceProviderListAdapter.ViewHolder holder, final int position) {

        final ServiceProviderData serviceProviderData = serviceProviderDataList.get(position);

        holder.serviceProviderName.setText(serviceProviderData.getServiceProviderName());
        holder.serviceProviderLocation.setText(serviceProviderData.getServiceProviderCounty());
        holder.serviceProviderTags.setText(serviceProviderData.getServiceProviderSubCounty());

        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, ServiceProviderHome.class);
                mContext.startActivity(intent);

            }
        });


        holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                holder.radioButton.setChecked(true);
                notifyDataSetChanged();


            }
        });

        //load image using glide
        Glide.with(mContext).load(R.drawable.book).into(holder.serviceProviderImage);

    }

    @Override
    public int getItemCount() {
        return serviceProviderDataList.size();
    }
}
