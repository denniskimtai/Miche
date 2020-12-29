package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PickCropFragment extends Fragment {

    private List<PickCropData> cropList;
    private RecyclerView recyclerView;
    private PickCropListAdapter pickCropListAdapter;

    private ProgressDialog progressDialog;

    private AlertDialog.Builder alertDialogBuilder;

    private static final String URL_CROP = "http://limasmart.zuriservices.com/limasmart_app/fetch_crops.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_pick_crop, container, false);

        progressDialog = new ProgressDialog(getActivity());

        alertDialogBuilder = new AlertDialog.Builder(getActivity());

        //initialize views
        recyclerView = myView.findViewById(R.id.recyclerView);
        cropList = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);

        loadCrops();

        return myView;

    }


    private void loadCrops(){

        progressDialog.setMessage("Loading crops...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //check if network is connected
        if (!isNetworkAvailable()){
            progressDialog.dismiss();

            alertDialogBuilder.setTitle("Network Failure");
            alertDialogBuilder.setMessage("Please check your internet connection!");
            alertDialogBuilder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    loadCrops();

                }
            });
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_CROP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                String cropName = product.getString("cropName");
                                String cropImage = product.getString("cropImage");
                                int id = Integer.parseInt(product.getString("id"));
                                String general_info = product.getString("general_info");

                                PickCropData pickCropData = new PickCropData(cropName, cropImage, id, general_info);
                                cropList.add(pickCropData);

                            }

                            //creating adapter object and setting it to recyclerview
                            pickCropListAdapter = new PickCropListAdapter(getActivity(), cropList);
                            recyclerView.setAdapter(pickCropListAdapter);

                            progressDialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        alertDialogBuilder.setTitle("Error occurred");
                        alertDialogBuilder.setMessage("Please ensure you have stable internet connection!");
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                loadCrops();
                            }
                        });

                        alertDialogBuilder.show();

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);




    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}