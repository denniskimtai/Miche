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

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropDetailsActivity extends AppCompatActivity {

    private List<ServiceProviderData> serviceProviderDataList;
    private List<tags> tagsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ServiceProviderListAdapter serviceProviderListAdapter;
    private TextView stepTitle, stepDesc, cropName;

    private String stepDescriptionText, stepNameText, crop_name, stepId, crop_id;

    private ProgressDialog progressDialog;
    private android.app.AlertDialog.Builder alertDialogBuilder;

    private RecyclerView tagsRecyclerView;
    private HorizontalTagsAdapter horizontalTagsAdapter;

    private final String URL_TAGS = "http://limasmart.zuriservices.com/limasmart_app/get_tags.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_details);

        stepDescriptionText = getIntent().getStringExtra("clicked_card");
        stepNameText = getIntent().getStringExtra("step_title");
        crop_name = getIntent().getStringExtra("crop_name");
        stepId = getIntent().getStringExtra("step_id");
        crop_id = getIntent().getStringExtra("crop_id");

        progressDialog = new ProgressDialog(this);

        alertDialogBuilder = new android.app.AlertDialog.Builder(this);

        tagsRecyclerView = findViewById(R.id.tagsRecyclerview);
        RecyclerView.LayoutManager tagsLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        tagsRecyclerView.setLayoutManager(tagsLayoutManager);

        stepTitle = findViewById(R.id.step_title);
        stepTitle.setText(stepNameText);

        stepDesc = findViewById(R.id.stepDesc);
        stepDesc.setText(stepDescriptionText);

        cropName = findViewById(R.id.crop_name);
        cropName.setText(crop_name);

        //initialize views
        recyclerView = findViewById(R.id.recyclerView);
        serviceProviderDataList = new ArrayList<>();
        serviceProviderListAdapter = new ServiceProviderListAdapter(this, serviceProviderDataList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(serviceProviderListAdapter);

        stepTags();
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

    //get step tags
    private void stepTags(){

        progressDialog.setMessage("Loading...");
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

                    stepTags();

                }
            });
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_TAGS,
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

                                String tagId = product.getString("tagId");
                                String tagName = product.getString("tagName");

                                tags tags = new tags(tagId, tagName);
                                tagsList.add(tags);


                            }

                            horizontalTagsAdapter = new HorizontalTagsAdapter(CropDetailsActivity.this, tagsList);
                            tagsRecyclerView.setAdapter(horizontalTagsAdapter);
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
                        alertDialogBuilder.setMessage("Please ensure you have stable internet connection!" + error.getMessage());
                        alertDialogBuilder.setCancelable(false);
                        alertDialogBuilder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                stepTags();
                            }
                        });

                        alertDialogBuilder.show();

                    }
                }){
            //send params needed to db
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("crop_id", crop_id);
                params.put("step_id", stepId);

                return params;

            }
        };

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
