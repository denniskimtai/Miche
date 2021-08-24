package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ServiceProviderListAdapter extends RecyclerView.Adapter<ServiceProviderListAdapter.ViewHolder> {

    public Context mContext;
    private List<ServiceProviderData> serviceProviderDataList;
    private String stepId;

    private SQLiteDatabase mDatabase;


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

    public ServiceProviderListAdapter(Context mContext, List<ServiceProviderData> serviceProviderDataList, String stepId) {
        this.mContext = mContext;
        this.serviceProviderDataList = serviceProviderDataList;
        this.stepId = stepId;
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

        //################################################
        //set checked service providers
        mDatabase = mContext.openOrCreateDatabase("serviceProviderSelectionDB", Context.MODE_PRIVATE, null);

        //we used rawQuery(sql, selectionargs) for fetching all the service providers
        String sqliteQuery = "SELECT serviceProviderId FROM tblSelection WHERE stepId = ?";

        Cursor cursor = mDatabase.rawQuery(sqliteQuery, new String[] {stepId});

        //if the cursor has some data
        if (cursor.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record
                if (serviceProviderData.getServiceProviderId().equals(String.valueOf(cursor.getInt(0)))){

                    holder.radioButton.setChecked(true);
                }

            } while (cursor.moveToNext());
        }
        //closing the cursor
        cursor.close();

        //#############################################


        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext, serviceProviderData.getServiceProviderName(), Toast.LENGTH_SHORT).show();

            }
        });


        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final boolean isChecked = holder.radioButton.isChecked();

                if (isChecked){
                    //add to sqlite database
                    //creating a database

                    mDatabase = mContext.openOrCreateDatabase("serviceProviderSelectionDB", Context.MODE_PRIVATE, null);
                    createSelectionTable();

                    //insert into table
                    String insertSQL = "INSERT INTO tblSelection \n" +
                            "(stepId, serviceProviderId)\n" +
                            "VALUES \n" +
                            "(?, ?);";

                    //using the same method execsql for inserting values
                    //this time it has two parameters
                    //first is the sql string and second is the parameters that is to be binded with the query
                    mDatabase.execSQL(insertSQL, new String[]{stepId, serviceProviderData.getServiceProviderId()});

                    Toast.makeText(mContext, "Added Successfully", Toast.LENGTH_SHORT).show();

                }else{
                    //remove from db
                    String sql = "DELETE FROM tblSelection WHERE serviceProviderId = ? AND stepId = ?";
                    mDatabase.execSQL(sql, new String[]{serviceProviderData.getServiceProviderId(), stepId});

                    Toast.makeText(mContext, "removed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //load image using glide
        Glide.with(mContext).load(R.drawable.book).into(holder.serviceProviderImage);

    }

    @Override
    public int getItemCount() {
        return serviceProviderDataList.size();
    }

    private void readSelectedServiceProviders(){

        mDatabase = mContext.openOrCreateDatabase("serviceProviderSelectionDB", Context.MODE_PRIVATE, null);

        //we used rawQuery(sql, selectionargs) for fetching all the service providers
        String sqliteQuery = "SELECT serviceProviderId FROM tblSelection WHERE stepId = ?";

        Cursor cursor = mDatabase.rawQuery(sqliteQuery, new String[] {stepId});

        //if the cursor has some data
        if (cursor.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                cursor.getInt(0);

            } while (cursor.moveToNext());
        }
        //closing the cursor
        cursor.close();

    }

    //In this method we will do the create operation

    private void createSelectionTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS tblSelection (\n" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                        "    stepId varchar(200) NOT NULL,\n" +
                        "    serviceProviderId varchar(200) NOT NULL\n" +
                        ");"
        );
    }

}
