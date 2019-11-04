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
import com.ziasy.vaishnavvivah.model.OtpModel;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * Created by ANDROID on 10-May-18.
 */

public class Forgot extends AppCompatActivity implements View.OnClickListener{

    EditText etMobile;
    Button btnSend;
    APIInterface apiInterface;
    ProgressDialog pDialog;
    String mobile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);
        pDialog = new ProgressDialog(Forgot.this);
        pDialog.setMessage("please wait....");
        pDialog.setCancelable(false);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        etMobile=(EditText)findViewById(R.id.etMob);
        btnSend=(Button) findViewById(R.id.btnSend);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");

        btnSend.setOnClickListener(this);
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




    public void forgotpassword(String s) {
        JsonObject object=new JsonObject();
        object.addProperty("user_mobile",s);
        pDialog.show();
        Call<OtpModel> call = apiInterface.getForgot(object);
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, retrofit2.Response<OtpModel> response) {
                pDialog.dismiss();
                try {
                    OtpModel resource = response.body();
                    if (resource.status.equals("success")) {
                        Toast.makeText(Forgot.this, "Your Otp is " + resource.responce, Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(Forgot.this, Forgot_password.class);
                        in.putExtra("otp", resource.responce);
                        in.putExtra("mobile", mobile);
                        startActivity(in);
                        finish();
                    } else if (resource.status.equals("user not registered")) {
                        Toast.makeText(Forgot.this, "This Number Is Not Registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Forgot.this, "Something went wrong...", Toast.LENGTH_SHORT).show();

                    }
                }catch (Exception e){
                    pDialog.dismiss();
                    Toast.makeText(Forgot.this, "please check your internet connection...", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {
                call.cancel();
                Toast.makeText(Forgot.this, "please try again..!", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnSend:
                mobile = etMobile.getText().toString();
                if(mobile.equals("")){
                    etMobile.setError("Please enter mobile no");
                }else if(mobile.length()<10){
                    etMobile.setError("Enter 10 digit No");
                }else {
                    forgotpassword(mobile);
                }
        }
    }
}
