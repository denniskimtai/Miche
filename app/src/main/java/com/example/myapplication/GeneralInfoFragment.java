package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GeneralInfoFragment extends Fragment {

    private List<ServiceProviderData> serviceProviderDataList;
    private RecyclerView recyclerView;
    private ServiceProviderListAdapter serviceProviderListAdapter;
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_general_info, container, false);


        //initialize views
        recyclerView = myView.findViewById(R.id.recyclerView);
        serviceProviderDataList = new ArrayList<>();
        serviceProviderListAdapter = new ServiceProviderListAdapter(getActivity(), serviceProviderDataList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(serviceProviderListAdapter);

        loadNews();

        return myView;
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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");
    }

}


