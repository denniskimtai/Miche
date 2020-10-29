package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class CropDetailsActivity extends AppCompatActivity {

    private List<ServiceProviderData> serviceProviderDataList;
    private RecyclerView recyclerView;
    private ServiceProviderListAdapter serviceProviderListAdapter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);

        //initialize views
        recyclerView = findViewById(R.id.recyclerView);
        serviceProviderDataList = new ArrayList<>();
        serviceProviderListAdapter = new ServiceProviderListAdapter(this, serviceProviderDataList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(serviceProviderListAdapter);

        loadNews();

    }

    private void loadNews() {

        int[] serviceProviderImages = new int[]{
                R.drawable.book,
                R.drawable.book2
        };

        ServiceProviderData a = new ServiceProviderData("Soil Cares", "Ainabkoi","pH" ,serviceProviderImages[0]);
        serviceProviderDataList.add(a);

        a = new ServiceProviderData("Lima Smart", "Kenmosa", "Sampling" , serviceProviderImages[1]);
        serviceProviderDataList.add(a);

        a = new ServiceProviderData("Cropnuts", "Kapseret", "Testing" , serviceProviderImages[0]);
        serviceProviderDataList.add(a);

        serviceProviderListAdapter.notifyDataSetChanged();

    }

}
