package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {

    private List<NewsData> newsDataList;
    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Returning the layout file after inflating
        View myView = inflater.inflate(R.layout.fragment_news, container, false);

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
                R.drawable.book2,
                R.drawable.cabbage,
                R.drawable.orange
        };

        NewsData a = new NewsData("Sygenta Weekly Fertilizer Manual", "https://www.syngenta.co.ke/weeklymanual", newsImages[0]);
        newsDataList.add(a);

        a = new NewsData("Horti Grid Passion Farming Manual", "https://www.hortirid.co.ke/passionfarmingmanual", newsImages[1]);
        newsDataList.add(a);

        a = new NewsData("Cabbage Farming Manual", "https://www.hortirid.co.ke/cabbagefarmingmanual", newsImages[2]);
        newsDataList.add(a);

        a = new NewsData("Orange Farming Manual", "https://www.hortirid.co.ke/orangefarmingmanual", newsImages[3]);
        newsDataList.add(a);

        newsAdapter.notifyDataSetChanged();

    }
}
