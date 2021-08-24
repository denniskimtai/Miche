package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.util.UUID;

public class FarmersProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSaveChanges;

    private AlertDialog.Builder alertDialogBuilder;

    private EditText edtFName, edtLName, edtCounty, edtSubCounty, edtEmailAddress, edtPhoneNumber;

    private String strFName, strLName, strCounty, strSubCounty, strEmailAddress, strPhoneNumber;

    private TextView txtSignOut;

    private ImageView profileImage;

    private final String PROFILE_IMAGE_UPLOAD_URL = "http://limasmart.zuriservices.com/limasmart_app/upload_profile_image.php";

    private ProgressDialog dialog;

    Uri imageUri;
    public static final int IMAGE = 1;

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

        //multipart upload namespace
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;

        alertDialogBuilder = new AlertDialog.Builder(this);

        dialog = new ProgressDialog(this);

        profileImage = findViewById(R.id.profile_image);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestStoragePermission();
                selectImage();

            }
        });

        txtSignOut = findViewById(R.id.txt_sign_out);
        txtSignOut.setOnClickListener(this);

        //getting the current user
        user userDetails = SharedPrefManager.getInstance(FarmersProfileActivity.this).getUser();

        edtFName.setText(userDetails.getfName());
        edtLName.setText(userDetails.getlName());
        edtCounty.setText(userDetails.getCounty());
        edtSubCounty.setText(userDetails.getSubCounty());
        edtEmailAddress.setText(userDetails.getEmail());
        edtPhoneNumber.setText(userDetails.getPhoneNumber());

        if (TextUtils.isEmpty(userDetails.getImage())){

            profileImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_profile));

        }else {

            Glide.with(this).load(userDetails.getImage()).into(profileImage);

        }

        alertDialogBuilder = new AlertDialog.Builder(this);

        btnSaveChanges = findViewById(R.id.fab_create_account);

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.txt_sign_out:
                SharedPrefManager.getInstance(this).logout();
                finish();
                break;
        }

    }

    private void selectImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE) {
            imageUri = data.getData();
            uploadImage();
        }
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Permission not Granted", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void uploadImage() {

        user user = SharedPrefManager.getInstance(this).getUser();

        try {

            String uploadId = UUID.randomUUID().toString();

            new MultipartUploadRequest(FarmersProfileActivity.this, uploadId,
                    PROFILE_IMAGE_UPLOAD_URL)
                    .addFileToUpload(getPath(imageUri), "profile_image")
                    .addParameter("user_id", String.valueOf(user.getId()))
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(Context context, UploadInfo uploadInfo) {

                            dialog.setMessage("Uploading profile image.\nPlease wait..");
                            dialog.setCancelable(false);
                            dialog.show();

                        }

                        @Override
                        public void onError(Context context, UploadInfo uploadInfo, ServerResponse serverResponse, Exception exception) {

                            dialog.dismiss();

                            alertDialogBuilder.setTitle("Failed!");
                            alertDialogBuilder.setMessage("Profile image not uploaded! Please try uploading again");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialogBuilder.show();
                            Toast.makeText(context, exception.getMessage() , Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onCompleted(Context context, UploadInfo uploadInfo, ServerResponse serverResponse) {

                            dialog.dismiss();

                            Toast.makeText(context, "Profile image uploaded successfully. Please login again", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(FarmersProfileActivity.this, LoginActivity.class);
                            startActivity(intent);

                            SharedPrefManager.getInstance(FarmersProfileActivity.this).logout();
                            finish();

                        }

                        @Override
                        public void onCancelled(Context context, UploadInfo uploadInfo) {

                            if(dialog.isShowing()){
                                dialog.dismiss();
                            }

                            alertDialogBuilder.setTitle("Failed!");
                            alertDialogBuilder.setMessage("Profile image not uploaded! Please try uploading again");
                            alertDialogBuilder.setCancelable(false);
                            alertDialogBuilder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alertDialogBuilder.show();

                        }
                    })

                    .startUpload();

        } catch (Exception e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }


    }

    private String getPath(Uri uri) {
        String path = null;

        Cursor cursor = this.getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        String document_id = cursor.getString(0);

        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = this.getApplicationContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        if (cursor != null && cursor.moveToFirst()) {

            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }


        return path;
    }

}