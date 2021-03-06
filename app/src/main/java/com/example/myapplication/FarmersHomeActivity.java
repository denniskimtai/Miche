package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FarmersHomeActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    private TextView txtUserName, txtCounty, txtSubCounty;

    private LinearLayout profileLayout;

    private ImageView profileImage;

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

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPrefManager.getInstance(FarmersHomeActivity.this).logout();


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
}