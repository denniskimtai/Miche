package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CropStepsActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    private AlertDialog.Builder builder;

    private ProgressDialog progressDialog;

    private String crop_name, crop_id;

    private List<stepDesc> stepDescList = new ArrayList<>();

    private android.app.AlertDialog.Builder alertDialogBuilder;

    private String URL_CROP_STEPS_DESC = "http://limasmart.zuriservices.com/limasmart_app/crop_steps.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_steps);

        crop_id = String.valueOf(getIntent().getIntExtra("crop_id", 0));
        crop_name = getIntent().getStringExtra("crop_name");
        String general_info = getIntent().getStringExtra("general_info");

        progressDialog = new ProgressDialog(this);

        alertDialogBuilder = new android.app.AlertDialog.Builder(this);

        gridLayout = findViewById(R.id.mainGrid);

        setSingleEvent(gridLayout);

        builder = new AlertDialog.Builder(this);

        builder.setTitle(crop_name);

        builder.setIcon(R.drawable.logo);

        //show pop up dialog with information
        builder.setMessage(general_info + "\n\n" + "Please follow the steps below to get a full summary of what you need to cultivate it. \n\n"+
                "For agronomical support services click on the Lima Smart button \n\n");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                getStepDesc();

            }
        });

        // change text size of alert dialog builder
        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().getAttributes();

        TextView textView = alert.findViewById(android.R.id.message);
        textView.setTextSize(13);



    }

    // we are setting onClickListener for each element in gridlayout
    private void setSingleEvent(GridLayout gridLayout) {
        for(int i = 0; i<gridLayout.getChildCount();i++){
            CardView cardView = (CardView)gridLayout.getChildAt(i);
            final int finalI= i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (finalI < 8){

                        stepDesc stepDesc = stepDescList.get(finalI);

                        Intent intent = new Intent(CropStepsActivity.this, CropDetailsActivity.class);
                        intent.putExtra("clicked_card", stepDesc.getStepDesc());
                        intent.putExtra("step_title", stepDesc.getStepName());
                        intent.putExtra("crop_name", crop_name);
                        intent.putExtra("step_id", stepDesc.getStepId());
                        intent.putExtra("crop_id", crop_id);
                        intent.putExtra("clicked_step_no", finalI);

                        //pass step descriptions
                        Bundle bundle = new Bundle();
                        //add list to Bundle
                        bundle.putParcelableArrayList("StepDescList", (ArrayList<? extends Parcelable>) stepDescList);
                        // add Bundle to intent
                        intent.putExtras(bundle);

                        startActivity(intent);

                    }else{

                        //get summary of all selected service providers go to summary page
                        Intent intent = new Intent(CropStepsActivity.this, SummaryActivity.class);
                        startActivity(intent);


                    }



                }
            });
        }
    }

    private void getStepDesc(){

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

                    getStepDesc();

                }
            });
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.show();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CROP_STEPS_DESC,
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

                                String stepId = product.getString("stepId");
                                String stepName = product.getString("stepName");
                                String stepDesc = product.getString("stepDesc");

                                stepDesc stepDesc1 = new stepDesc(stepId, stepName, stepDesc);
                                stepDescList.add(stepDesc1);

                            }
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

                                getStepDesc();
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