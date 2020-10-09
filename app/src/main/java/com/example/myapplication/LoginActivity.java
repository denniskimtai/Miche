package com.example.myapplication;import androidx.appcompat.app.AppCompatActivity;import android.content.Intent;import android.os.Bundle;import android.text.TextUtils;import android.view.View;import android.widget.Button;import android.widget.EditText;import android.widget.TextView;public class LoginActivity extends AppCompatActivity {    private TextView textViewSignUp, textViewRegisterHere;    private Button btnLogin;    private EditText editTextEmail;    @Override    protected void onCreate(Bundle savedInstanceState) {        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_login);        btnLogin = findViewById(R.id.fab_login);        textViewRegisterHere = findViewById(R.id.txt_signup_here);        editTextEmail = findViewById(R.id.edit_text_email);        btnLogin.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                login();            }        });        textViewRegisterHere.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                //go to signup activity                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);                startActivity(intent);            }        });    }    private void login(){        String loginEmail = editTextEmail.getText().toString();        if (TextUtils.isEmpty(loginEmail)){            editTextEmail.setError("Please enter your email address!");            return;        }else if(loginEmail.equals("farmer@miche.com")){            //go to home activity            Intent intent = new Intent(LoginActivity.this, FarmersHomeActivity.class);            startActivity(intent);        }else if (loginEmail.equals("serviceprovider@miche.com")){            //go to service provider dashboard            Intent intent = new Intent(LoginActivity.this, ServiceProviderHome.class);            startActivity(intent);        }    }}