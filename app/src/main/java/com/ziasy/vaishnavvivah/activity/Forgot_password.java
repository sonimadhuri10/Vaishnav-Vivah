package com.ziasy.vaishnavvivah.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.model.Login_model;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by ANDROID on 10-May-18.
 */

public class Forgot_password extends AppCompatActivity implements View.OnClickListener {

    EditText etOtp, etNewPassword;
    Button btnSubmit;
    String newpassword = "", otp = "",mobile="",otpnew="";
    APIInterface apiInterface;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_activity);

        etOtp = (EditText) findViewById(R.id.etOtp);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        btnSubmit = (Button) findViewById(R.id.submit);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        pDialog = new ProgressDialog(Forgot_password.this);
        pDialog.setMessage("please wait....");
        pDialog.setCancelable(false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reset Password");

        btnSubmit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.submit:
                validation();
                break;

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }


    public void validation() {

        Intent in = getIntent();
        mobile =   in.getStringExtra("mobile");
        otp = in.getStringExtra("otp");

        newpassword = etNewPassword.getText().toString();
        otpnew = etOtp.getText().toString();

        if (!otpnew.equals(otp)) {
            etOtp.setError("Please enter valid Otp");
        } else if (newpassword.equals("")) {
            etNewPassword.setError("Please enter New Password");
        } else {
            JsonObject object=new JsonObject();
            object.addProperty("password",newpassword);
            object.addProperty("user_mobile",mobile);

            pDialog.show();
            Call<Login_model> call = apiInterface.getUpdatePassword(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pDialog.dismiss();
                    try {
                            Login_model resource = response.body();
                            if (resource.status.equals("success")) {
                                Toast.makeText(Forgot_password.this, "Your password has updated suceessfully", Toast.LENGTH_SHORT).show();
                                Intent in = new Intent(Forgot_password.this, loginActivity.class);
                                startActivity(in);
                                finish();
                            } else {
                                Toast.makeText(Forgot_password.this, resource.status, Toast.LENGTH_SHORT).show();

                            }
                        }catch (Exception e){
                        pDialog.dismiss();
                        Toast.makeText(Forgot_password.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(Forgot_password.this, "please try again....", Toast.LENGTH_SHORT).show();
                    pDialog.dismiss();
                }
            });

        }

    }

}



