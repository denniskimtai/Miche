package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ServiceProviderProfile extends AppCompatActivity implements View.OnClickListener {

    private EditText edtCompanyName, edtCounty, edtSubCounty, edtEmailAddress, edtPhoneNumber, edtCategory, edtService, edtWebsite;
    private Button createAccount;
    private TextView txtSignOut;

    private String strCompanyName, strCounty, strSubCounty, strEmailAddress, strPhoneNumber, strCategory, strService, strWebsite;

    private AlertDialog.Builder alertDialogBuilder;
    private ProgressDialog progressDialog;

    private String UPDATE_INFO_URL = "http://limasmart.zuriservices.com/limasmart_app/update_service_provider_info.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_profile);

        //getting the current user
        user userDetails = SharedPrefManager.getInstance(this).getUser();

        //initialize views
        edtCompanyName = findViewById(R.id.txt_company_name);
        edtCompanyName.setText(userDetails.getCompanyName());

        edtCounty = findViewById(R.id.txt_county);
        edtCounty.setText(userDetails.getCounty());

        edtSubCounty = findViewById(R.id.txt_sub_county);
        edtSubCounty.setText(userDetails.getSubCounty());

        edtEmailAddress = findViewById(R.id.txt_email_address);
        edtEmailAddress.setText(userDetails.getEmail());

        edtPhoneNumber = findViewById(R.id.txt_phone_number);
        edtPhoneNumber.setText(userDetails.getPhoneNumber());

        edtWebsite = findViewById(R.id.txt_website);
        edtWebsite.setText(userDetails.getWebsite());

        edtCategory = findViewById(R.id.txt_category);

        createAccount = findViewById(R.id.fab_create_account);
        createAccount.setOnClickListener(this);

        alertDialogBuilder = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);

        txtSignOut = findViewById(R.id.txt_sign_out);
        txtSignOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.fab_create_account:
                updateServiceProviderInfo();
                break;

            case R.id.txt_sign_out:
                SharedPrefManager.getInstance(this).logout();
                finish();
                break;

        }

    }

    private void updateServiceProviderInfo(){

        if (!isNetworkAvailable()){

            alertDialogBuilder.setTitle("Network Failure");
            alertDialogBuilder.setMessage("Please check your internet connection!");
            alertDialogBuilder.show();
            return;
        }

        //get text entered by user
        strCompanyName = edtCompanyName.getText().toString();

        //check if its empty
        if(TextUtils.isEmpty(strCompanyName)){
            edtCompanyName.setError("Please enter your company name");
            return;
        }

//        //get text entered by user
//        strCategory = edtCategory.getText().toString();
//
//        //check if its empty
//        if(TextUtils.isEmpty(strCategory)){
//            edtCategory.setError("Please enter your category");
//            return;
//        }

        //get text entered by user
        strCounty = edtCounty.getText().toString();

        //check if its empty
        if(TextUtils.isEmpty(strCounty)){
            edtCounty.setError("Please enter your county");
            return;
        }

        //get text entered by user
        strSubCounty = edtSubCounty.getText().toString();

        //check if its empty
        if(TextUtils.isEmpty(strSubCounty)){
            edtSubCounty.setError("Please enter your sub county");
            return;
        }

        //get text entered by user
        strEmailAddress = edtEmailAddress.getText().toString();

        //check if its empty
        if(TextUtils.isEmpty(strEmailAddress)){
            edtEmailAddress.setError("Please enter your Email Address");
            return;
        }

        //get text entered by user
        strPhoneNumber = edtPhoneNumber.getText().toString();

        //check if its empty
        if(TextUtils.isEmpty(strPhoneNumber)){
            edtPhoneNumber.setError("Please enter your phone number");
            return;
        }

        //get text entered by user
        strWebsite = edtWebsite.getText().toString();

        //check if its empty
        if(TextUtils.isEmpty(strWebsite)){
            edtWebsite.setError("Please enter your company website");
            return;
        }

        progressDialog.setMessage("Creating account...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Sign up and fetch result from database
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, UPDATE_INFO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //fetch json object returned by api
                        switch (response) {

                            case "successful":

                                Toast.makeText(ServiceProviderProfile.this, "Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ServiceProviderProfile.this, LoginActivity.class);
                                startActivity(intent);
                                SharedPrefManager.getInstance(ServiceProviderProfile.this).logout();
                                finish();

                                break;


                            case "unsuccessful":
                                Toast.makeText(ServiceProviderProfile.this, "Unsuccessful please try again", Toast.LENGTH_SHORT).show();

                                break;

                        }
                        progressDialog.dismiss();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(ServiceProviderProfile.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            //send params needed to db
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                //getting the current user
                user userDetails = SharedPrefManager.getInstance(ServiceProviderProfile.this).getUser();

                params.put("user_id", String.valueOf(userDetails.getId()));
                params.put("company_name", strCompanyName);
                params.put("county", strCounty);
                params.put("sub_county", strSubCounty);
                params.put("email", strEmailAddress);
                params.put("phone_number", strPhoneNumber);
                params.put("website", strWebsite);

                return params;

            }
        };

        Volley.newRequestQueue(ServiceProviderProfile.this).add(stringRequest);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}