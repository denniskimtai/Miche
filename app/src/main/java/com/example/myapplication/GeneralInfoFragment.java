package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class GeneralInfoFragment extends Fragment {

    private List<NewsData> newsDataList;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_general_info, container, false);


        //initialize views
        recyclerView = myView.findViewById(R.id.recyclerView);
        newsDataList = new ArrayList<>();
        newsAdapter = new NewsAdapter(getActivity(), newsDataList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(newsAdapter);

        loadNews();

        return myView;
    }

    private void loadNews() {

        int[] newsImages = new int[]{
                R.drawable.book,
                R.drawable.book2
        };

        NewsData a = new NewsData("Sygenta", "Uasin Gishu-Moiben", newsImages[0]);
        newsDataList.add(a);

        a = new NewsData("Horti Grid", "Nairobi County-Utawala", newsImages[1]);
        newsDataList.add(a);

        newsAdapter.notifyDataSetChanged();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Menu 1");
    }

}


