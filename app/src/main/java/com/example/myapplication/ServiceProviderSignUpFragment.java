package com.example.myapplication;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class ServiceProviderSignUpFragment extends Fragment {

    private AlertDialog.Builder alertDialogBuilder;
    private ProgressDialog progressDialog;

    private Button createAccount;

    private TextView txtLogin;

    private EditText edtCompanyName, edtCounty, edtSubCounty, edtEmailAddress, edtPhoneNumber, edtCategory, edtService, edtPassword, edtConfirmPassword;

    private String strCompanyName, strCounty, strSubCounty, strEmailAddress, strPhoneNumber, strCategory, strService, strPassword, strConfirmPassword;

    private String register_url = "http://limasmart.zuriservices.com/limasmart_app/user_registration.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_service_provider_sign_up, container, false);

        //initialize views
        edtCompanyName = myView.findViewById(R.id.txt_company_name);
        edtCounty = myView.findViewById(R.id.txt_county);
        edtSubCounty = myView.findViewById(R.id.txt_sub_county);
        edtEmailAddress = myView.findViewById(R.id.txt_email_address);
        edtPhoneNumber = myView.findViewById(R.id.txt_phone_number);
        edtCategory = myView.findViewById(R.id.txt_category);
        edtPassword = myView.findViewById(R.id.txt_password);
        edtConfirmPassword = myView.findViewById(R.id.txt_confirm_password);

        createAccount = myView.findViewById(R.id.fab_create_account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signUp();

            }
        });

        txtLogin = myView.findViewById(R.id.txt_signup_here);

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

        alertDialogBuilder = new AlertDialog.Builder(getActivity());
        progressDialog = new ProgressDialog(getActivity());



        return myView;

    }


    private void signUp(){

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

        //get text entered by user
        strCategory = edtCategory.getText().toString();

        //check if its empty
        if(TextUtils.isEmpty(strCategory)){
            edtCategory.setError("Please enter your category");
            return;
        }

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
        strPassword = edtPassword.getText().toString();

        //check if its empty
        if(TextUtils.isEmpty(strPassword)){
            edtPassword.setError("Please enter your password");
            return;
        }

        //get text entered by user
        strConfirmPassword = edtConfirmPassword.getText().toString();

        //check if its empty
        if(TextUtils.isEmpty(strConfirmPassword)){
            edtConfirmPassword.setError("Please confirm your password");
            return;
        }

        //check if passwords match
        if (!strPassword.equals(strConfirmPassword))
        {
            edtConfirmPassword.setError("Passwords do not match");
            return;
        }

        progressDialog.setMessage("Creating account...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        //Sign up and fetch result from database
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST, register_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //fetch json object returned by api
                        switch (response) {

                            case "User already registered":
                                alertDialogBuilder.setTitle("Registration Failed");
                                alertDialogBuilder.setMessage("The email already exists in the system! Please login ");
                                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        Intent regintent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(regintent);
                                        getActivity().finish();

                                    }
                                });
                                alertDialogBuilder.show();

                                break;


                            case "registration successful":
                                Intent regintent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(regintent);
                                getActivity().finish();

                                break;

                            case "registration failed":
                                alertDialogBuilder.setTitle("Registration Failed");
                                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                alertDialogBuilder.setMessage("Please try again! \nEnsure you have internet connection and your credentials are correct");
                                alertDialogBuilder.show();

                                break;

                        }
                        progressDialog.dismiss();
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            //send params needed to db
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                String hashed = MD5_Hash(strPassword);
                params.put("FName", "");
                params.put("LName", "");
                params.put("County", strCounty);
                params.put("EmailAddress", strEmailAddress);
                params.put("PhoneNumber", strPhoneNumber);
                params.put("SubCounty", strSubCounty);
                params.put("Image", "");
                params.put("CompanyName", strCompanyName);
                params.put("Website", "");
                params.put("Password", hashed);
                params.put("userType", "2");

                return params;

            }
        };

        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String MD5_Hash (String s){
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }


}
