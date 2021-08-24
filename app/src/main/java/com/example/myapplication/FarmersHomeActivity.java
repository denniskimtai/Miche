package com.example.myapplication;

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

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadService;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.util.Objects;
import java.util.UUID;

public class FarmersHomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    private TextView txtUserName, txtCounty, txtSubCounty;

    private LinearLayout profileLayout;

    private ImageView profileImage;

    public static final int IMAGE = 1;
    public static final int CAMERA = 2;

    private AlertDialog.Builder alertDialogBuilder;

    private ProgressDialog dialog;

    private final String PROFILE_IMAGE_UPLOAD_URL = "http://limasmart.zuriservices.com/limasmart_app/upload_profile_image.php";

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmers_home);

        //initialize views
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final CollapsingToolbarLayout toolBarLayout = findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle("  ");

        txtUserName = findViewById(R.id.user_name);
        txtCounty = findViewById(R.id.county);
        txtSubCounty = findViewById(R.id.sub_county);

        profileImage = findViewById(R.id.profile_image);

        alertDialogBuilder = new AlertDialog.Builder(this);

        dialog = new ProgressDialog(this);

        //multipart upload namespace
        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID;

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestStoragePermission();
                selectImage();

            }
        });

        profileLayout = findViewById(R.id.profile);

        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FarmersHomeActivity.this, FarmersProfileActivity.class);
                startActivity(intent);
            }
        });

        //getting the current user
        user userDetails = SharedPrefManager.getInstance(FarmersHomeActivity.this).getUser();

        //set text to the fields
        txtUserName.setText("Welcome " + userDetails.getfName() + " " + userDetails.getlName());
        txtCounty.setText(userDetails.getCounty());
        txtSubCounty.setText(userDetails.getSubCounty());

        if (TextUtils.isEmpty(userDetails.getImage())){

            profileImage.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.icon_profile));

        }else {

            Glide.with(this).load(userDetails.getImage()).into(profileImage);

        }


        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);

        //tablayout menu
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //Adding the tabs name
        tabLayout.addTab(tabLayout.newTab().setText("Pick Crop"));
        tabLayout.addTab(tabLayout.newTab().setText("Vendors"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

//        // hiding & showing the title when toolbar expanded & collapsed
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    toolBarLayout.setTitle("Rachel's Farm");
//                    isShow = true;
//                } else if (isShow) {
//                    toolBarLayout.setTitle(" ");
//                    isShow = false;
//                }
//            }
//        });


        //Initializing viewPager
        viewPager = findViewById(R.id.pager);

        //Creating our pager adapter
        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //tablayout menus
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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

            new MultipartUploadRequest(FarmersHomeActivity.this, uploadId,
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

                            Intent intent = new Intent(FarmersHomeActivity.this, LoginActivity.class);
                            startActivity(intent);

                            SharedPrefManager.getInstance(FarmersHomeActivity.this).logout();
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