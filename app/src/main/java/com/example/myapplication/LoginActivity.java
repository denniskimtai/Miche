package com.example.myapplication;import androidx.appcompat.app.AppCompatActivity;import android.annotation.SuppressLint;import android.app.AlertDialog;import android.app.ProgressDialog;import android.content.Context;import android.content.DialogInterface;import android.content.Intent;import android.net.ConnectivityManager;import android.net.NetworkInfo;import android.os.Bundle;import android.text.TextUtils;import android.view.View;import android.widget.Button;import android.widget.EditText;import android.widget.TextView;import android.widget.Toast;import com.android.volley.AuthFailureError;import com.android.volley.Request;import com.android.volley.Response;import com.android.volley.VolleyError;import com.android.volley.toolbox.StringRequest;import com.android.volley.toolbox.Volley;import org.json.JSONException;import org.json.JSONObject;import java.math.BigInteger;import java.security.MessageDigest;import java.security.NoSuchAlgorithmException;import java.util.HashMap;import java.util.Map;public class LoginActivity extends AppCompatActivity {    private TextView textViewSignUp, textViewRegisterHere;    private Button btnLogin;    private EditText editTextEmail, editTextPassword;    private ProgressDialog progressDialog;    private AlertDialog.Builder alertDialogBuilder;    String login_url = "http://limasmart.zuriservices.com/limasmart_app/login.php";    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_login);        if (SharedPrefManager.getInstance(this).isLoggedIn()) {            Intent intent = new Intent(LoginActivity.this, FarmersHomeActivity.class);            startActivity(intent);            return;        }        progressDialog = new ProgressDialog(this);        progressDialog.setMessage("Signing In. Please wait...");        alertDialogBuilder = new AlertDialog.Builder(this);        btnLogin = findViewById(R.id.fab_login);        textViewRegisterHere = findViewById(R.id.txt_signup_here);        editTextEmail = findViewById(R.id.edit_text_email);        editTextPassword = findViewById(R.id.edit_text_password);        btnLogin.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                login();            }        });        textViewRegisterHere.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                //go to sign up activity                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);                startActivity(intent);            }        });    }    private void login(){        //check if network is connected        if (!isNetworkAvailable()){            alertDialogBuilder.setTitle("Network Failure");            alertDialogBuilder.setMessage("Please check your internet connection!");            alertDialogBuilder.show();            return;        }        final String loginEmail = editTextEmail.getText().toString();        if (TextUtils.isEmpty(loginEmail)){            editTextEmail.setError("Please enter your email address!");            return;        }        final String loginPassword = editTextPassword.getText().toString();        if (TextUtils.isEmpty(loginPassword)){            editTextPassword.setError("Please enter your password!");            return;        }        progressDialog.show();//Login and fetch result from database        StringRequest stringRequest = new StringRequest(                Request.Method.POST,                login_url,                new Response.Listener<String>() {                    @Override                    public void onResponse(String response) {                    //fetch json object returned by api                    try {                        //converting response to json object                        JSONObject obj = new JSONObject(response);                        //if no error in response                        if (!obj.getBoolean("error")) {                            //Toast.makeText(this, obj.getString("message"), Toast.LENGTH_SHORT).show();                            //getting the user from the response                            JSONObject userJson = obj.getJSONObject("user");                            //creating a new user object                            user user = new user(                                    userJson.getInt("id"),                                    userJson.getString("userType"),                                    userJson.getString("email"),                                    userJson.getString("FName"),                                    userJson.getString("LName"),                                    userJson.getString("County"),                                    userJson.getString("phoneNumber"),                                    userJson.getString("subCounty"),                                    userJson.getString("image"),                                    userJson.getString("companyName"),                                    userJson.getString("website")                            );                            //storing the user in shared preferences                            SharedPrefManager.getInstance(LoginActivity.this).userLogin(user);                            //check type of user                            //getting the current user                            user userDetails = SharedPrefManager.getInstance(LoginActivity.this).getUser();                            if (userDetails.getUserType().equals("1")){                                //user is Farmer go to farmers activity                                Intent intent = new Intent(LoginActivity.this, FarmersHomeActivity.class);                                startActivity(intent);                            }else if (userDetails.getUserType().equals("2")){                                //user is a service provider go to service providers activity                                Intent intent = new Intent(LoginActivity.this, ServiceProviderHome.class);                                startActivity(intent);                            }                        } else {                            alertDialogBuilder.setTitle("Login Failed");                            alertDialogBuilder.setCancelable(false);                            alertDialogBuilder.setMessage("Please try again! \nEnsure you have internet connection and your credentials are correct");                            alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {                                @Override                                public void onClick(DialogInterface dialogInterface, int i) {                                }                            });                            alertDialogBuilder.show();                        }                        progressDialog.dismiss();                    } catch (JSONException e) {                        e.printStackTrace();                    }                }                }, new Response.ErrorListener() {            @Override            public void onErrorResponse(VolleyError error) {                progressDialog.dismiss();                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();            }        }){            //send params needed to db            @Override            protected Map<String, String> getParams() throws AuthFailureError {                Map<String, String> params = new HashMap<>();                String hashed = MD5_Hash(loginPassword);                params.put("emailAddress", loginEmail);                params.put("password", hashed);                return params;            }        };        Volley.newRequestQueue(this).add(stringRequest);    }    public boolean isNetworkAvailable() {        ConnectivityManager connectivityManager = (ConnectivityManager) this                .getSystemService(Context.CONNECTIVITY_SERVICE);        @SuppressLint("MissingPermission") NetworkInfo activeNetworkInfo = connectivityManager                .getActiveNetworkInfo();        return activeNetworkInfo != null && activeNetworkInfo.isConnected();    }    public static String MD5_Hash (String s){        MessageDigest m = null;        try {            m = MessageDigest.getInstance("MD5");        } catch (NoSuchAlgorithmException e) {            e.printStackTrace();        }        m.update(s.getBytes(), 0, s.length());        String hash = new BigInteger(1, m.digest()).toString(16);        return hash;    }}