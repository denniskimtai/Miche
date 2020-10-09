package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class PickCropFragment extends Fragment {

    private List<PickCropData> cropList;
    private RecyclerView recyclerView;
    private PickCropListAdapter pickCropListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_pick_crop, container, false);

        //initialize views
        recyclerView = myView.findViewById(R.id.recyclerView);
        cropList = new ArrayList<>();
        pickCropListAdapter = new PickCropListAdapter(getActivity(), cropList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(pickCropListAdapter);

        loadCrops();

        return myView;

    }




    private void loadCrops(){

        int[] cropImages = new int[]{
                R.drawable.skuma,
                R.drawable.cabbage,
                R.drawable.managu,
                R.drawable.passion,
                R.drawable.orange,
                R.drawable.skuma,
                R.drawable.cabbage,
                R.drawable.managu,
                R.drawable.passion,
                R.drawable.orange,
                R.drawable.cabbage,
                R.drawable.managu,
                R.drawable.passion,
                R.drawable.orange,
                R.drawable.cabbage,
                R.drawable.managu,
                R.drawable.passion,
                R.drawable.orange,
                R.drawable.skuma,
                R.drawable.cabbage,
                R.drawable.managu,
                R.drawable.passion,
                R.drawable.orange,
                R.drawable.cabbage,
                R.drawable.managu,
                R.drawable.passion,
                R.drawable.orange};

        PickCropData a = new PickCropData("Sukuma Wiki", cropImages[0]);
        cropList.add(a);

        a = new PickCropData("Cabbage", cropImages[1]);
        cropList.add(a);

        a = new PickCropData("Managu", cropImages[2]);
        cropList.add(a);

        a = new PickCropData("Passion", cropImages[3]);
        cropList.add(a);

        a = new PickCropData("Orange", cropImages[4]);
        cropList.add(a);

        a = new PickCropData("Sukuma Wiki", cropImages[5]);
        cropList.add(a);

        a = new PickCropData("Cabbage", cropImages[6]);
        cropList.add(a);

        a = new PickCropData("Managu", cropImages[7]);
        cropList.add(a);

        a = new PickCropData("Passion", cropImages[8]);
        cropList.add(a);

        a = new PickCropData("Orange", cropImages[9]);
        cropList.add(a);

        a = new PickCropData("Cabbage", cropImages[10]);
        cropList.add(a);

        a = new PickCropData("Managu", cropImages[11]);
        cropList.add(a);

        a = new PickCropData("Passion", cropImages[12]);
        cropList.add(a);

        a = new PickCropData("Orange", cropImages[13]);
        cropList.add(a);

        a = new PickCropData("Cabbage", cropImages[14]);
        cropList.add(a);

        a = new PickCropData("Managu", cropImages[15]);
        cropList.add(a);

        a = new PickCropData("Passion", cropImages[16]);
        cropList.add(a);

        a = new PickCropData("Orange", cropImages[17]);
        cropList.add(a);

        a = new PickCropData("Sukuma Wiki", cropImages[18]);
        cropList.add(a);

        a = new PickCropData("Cabbage", cropImages[19]);
        cropList.add(a);

        a = new PickCropData("Managu", cropImages[20]);
        cropList.add(a);

        a = new PickCropData("Passion", cropImages[21]);
        cropList.add(a);

        a = new PickCropData("Orange", cropImages[22]);
        cropList.add(a);

        a = new PickCropData("Cabbage", cropImages[23]);
        cropList.add(a);

        a = new PickCropData("Managu", cropImages[24]);
        cropList.add(a);

        a = new PickCropData("Passion", cropImages[25]);
        cropList.add(a);

        a = new PickCropData("Orange", cropImages[26]);
        cropList.add(a);

        pickCropListAdapter.notifyDataSetChanged();

    }
}