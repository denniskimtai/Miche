package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FarmersProfileActivity extends AppCompatActivity {

    private Button btnSaveChanges;

    private AlertDialog.Builder alertDialogBuilder;
    private ProgressDialog progressDialog;

    private EditText edtFName, edtLName, edtCounty, edtSubCounty, edtEmailAddress, edtPhoneNumber;

    private String strFName, strLName, strCounty, strSubCounty, strEmailAddress, strPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmers_profile);

        //initialize views
        edtFName = findViewById(R.id.txt_fName);
        edtLName = findViewById(R.id.txt_lName);
        edtCounty = findViewById(R.id.txt_county);
        edtSubCounty = findViewById(R.id.txt_subCounty);
        edtEmailAddress = findViewById(R.id.txt_emailAddress);
        edtPhoneNumber = findViewById(R.id.txt_phoneNumber);

        //getting the current user
        user userDetails = SharedPrefManager.getInstance(FarmersProfileActivity.this).getUser();

        edtFName.setText(userDetails.getfName());
        edtLName.setText(userDetails.getlName());
        edtCounty.setText(userDetails.getCounty());
        edtSubCounty.setText(userDetails.getSubCounty());
        edtEmailAddress.setText(userDetails.getEmail());
        edtPhoneNumber.setText(userDetails.getPhoneNumber());

        alertDialogBuilder = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);

        btnSaveChanges = findViewById(R.id.fab_create_account);

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }
}