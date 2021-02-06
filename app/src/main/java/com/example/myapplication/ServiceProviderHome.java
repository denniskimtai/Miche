package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServiceProviderHome extends AppCompatActivity implements View.OnClickListener {

    private ImageView profileDescAdd, additionalServicesAdd, newsAdd;

    private TextView textViewPhoneNumber, profileDescText, additionalServicesText;

    private LinearLayout dottedProfileDesc, dottedAdditionalServices;

    private CardView cardProfileDesc, cardAdditionalServices;

    private String text;

    private ProgressDialog progressDialog;
    private android.app.AlertDialog.Builder alertDialogBuilder;

    private final String SERVICE_PROVIDER_INFO_URL = "http://limasmart.zuriservices.com/limasmart_app/service_provider_info.php";

    private final String FETCH_SERVICE_PROVIDER_INFO_URL = "http://limasmart.zuriservices.com/limasmart_app/fetch_service_provider_info.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_home);

        //views initialization
        profileDescAdd = findViewById(R.id.profile_desc_add);
        profileDescAdd.setOnClickListener(this);

        additionalServicesAdd = findViewById(R.id.additional_services_add);
        additionalServicesAdd.setOnClickListener(this);

        newsAdd = findViewById(R.id.news_add);
        newsAdd.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving. Please wait...");

        dottedProfileDesc = findViewById(R.id.dotted_profile_desc);
        cardProfileDesc = findViewById(R.id.card_profile_desc);
        profileDescText = findViewById(R.id.profile_desc_text);

        dottedAdditionalServices = findViewById(R.id.dotted_additional_services);
        cardAdditionalServices = findViewById(R.id.card_additional_services);
        additionalServicesText = findViewById(R.id.additional_services_text);

        textViewPhoneNumber = findViewById(R.id.phone_number);

        fetchInfo();

        final String phoneNumber = textViewPhoneNumber.getText().toString();

        textViewPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.profile_desc_add:
                showCustomDialog("Profile Description", "profile_desc");
                break;

            case R.id.additional_services_add:
                showCustomDialog("Additional Services", "additional_services");
                break;

            case R.id.news_add:
                Toast.makeText(this, "coming soon", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    private void showCustomDialog(final String title, final String columnName) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.my_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        final AlertDialog alertDialog = builder.create();

        //onclick listener for button in alert dialog

        TextView titleText = dialogView.findViewById(R.id.title_text);
        titleText.setText(title);

        final EditText textEditText = dialogView.findViewById(R.id.text);

        Button btnSave = dialogView.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //check if field is empty
                text = textEditText.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    textEditText.setError("You have not entered any text. Please type in the box and try again.");
                    return;

                }

                progressDialog.show();

                //Login and fetch result from database
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        SERVICE_PROVIDER_INFO_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                switch (response) {

                                    case "successful":

                                        Toast.makeText(ServiceProviderHome.this, "Saved successfully!", Toast.LENGTH_LONG).show();
                                        alertDialog.dismiss();

                                        break;

                                    case "unsuccessful":

                                        Toast.makeText(ServiceProviderHome.this, "Failed. Ensure your internet connection is good and try again", Toast.LENGTH_LONG).show();

                                        break;

                                }

                                progressDialog.dismiss();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(ServiceProviderHome.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    //send params needed to db
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        //getting the current user
                        user userDetails = SharedPrefManager.getInstance(ServiceProviderHome.this).getUser();

                        params.put("user_id", String.valueOf(userDetails.getId()));
                        params.put("field_name", columnName);
                        params.put("text", text);

                        return params;

                    }
                };

                Volley.newRequestQueue(ServiceProviderHome.this).add(stringRequest);

            }
        });

        alertDialog.show();


    }

    private void fetchInfo() {

        //check if network is connected
        if (!isNetworkAvailable()) {

            alertDialogBuilder.setTitle("Network Failure");
            alertDialogBuilder.setMessage("Please check your internet connection!");
            alertDialogBuilder.show();
            return;
        }

        progressDialog.setMessage("Loading...");

        progressDialog.show();


//Login and fetch result from database
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                FETCH_SERVICE_PROVIDER_INFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(ServiceProviderHome.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //getting product object from json array
                            JSONObject product = array.getJSONObject(0);

                            String profile_desc = product.getString("profile_desc");
                            String additional_services = product.getString("additional_services");
                            String news = product.getString("news_id");


                            if (array.length() > 0) {

                                dottedProfileDesc.setVisibility(View.GONE);
                                cardProfileDesc.setVisibility(View.VISIBLE);
                                profileDescText.setText(profile_desc);


                                dottedAdditionalServices.setVisibility(View.GONE);
                                cardAdditionalServices.setVisibility(View.VISIBLE);
                                additionalServicesText.setText(additional_services);

                            }


                            progressDialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(ServiceProviderHome.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {
            //send params needed to db
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //getting the current user
                user userDetails = SharedPrefManager.getInstance(ServiceProviderHome.this).getUser();

                params.put("user_id", String.valueOf(userDetails.getId()));

                return params;

            }
        };

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