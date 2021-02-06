package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "limasmart";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_USERTYPE = "key_usertype";
    private static final String KEY_ID = "keyid";
    private static final String FNAME = "FNAME";
    private static final String LNAME = "LNAME";
    private static final String COUNTY = "COUNTY";
    private static final String WEBSITE = "WEBSITE";
    private static final String PHONENUMBER = "PHONENUMBER";
    private static final String SUBCOUNTY = "SUBCOUNTY";
    private static final String IMAGE = "IMAGE";
    private static final String COMPANY_NAME = "COMPANY_NAME";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(user user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_USERTYPE, String.valueOf(user.getUserType()));
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(FNAME, user.getfName());
        editor.putString(LNAME, user.getlName());
        editor.putString(COUNTY, user.getCounty());
        editor.putString(PHONENUMBER, user.getPhoneNumber());
        editor.putString(SUBCOUNTY, user.getSubCounty());
        editor.putString(IMAGE, user.getImage());
        editor.putString(COMPANY_NAME, user.getCompanyName());
        editor.putString(WEBSITE, user.getWebsite());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    //this method will give the logged in user
    public user getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new user(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_USERTYPE, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(FNAME, null),
                sharedPreferences.getString(LNAME, null),
                sharedPreferences.getString(COUNTY, null),
                sharedPreferences.getString(PHONENUMBER, null),
                sharedPreferences.getString(SUBCOUNTY, null),
                sharedPreferences.getString(IMAGE, null),
                sharedPreferences.getString(COMPANY_NAME, null),
                sharedPreferences.getString(WEBSITE, null)
        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}
