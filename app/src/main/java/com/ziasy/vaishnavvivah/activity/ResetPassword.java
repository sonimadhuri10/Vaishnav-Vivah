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
import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;
import com.ziasy.vaishnavvivah.model.OtpModel;

import retrofit2.Call;
import retrofit2.Callback;

public class ResetPassword extends AppCompatActivity {
    EditText etPassword, etNewPassword,etConfirmPassword ;
    Button btnChange ;
    SessionManagment sd ;
    ConnectionDetector cd ;
    ProgressDialog pd ;
    String password="",newpassword="",confirmPassword="";
    APIInterface apiInterface ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword_layout);

        etPassword=(EditText)findViewById(R.id.etPassword);
        etNewPassword=(EditText)findViewById(R.id.etNewPassword);
        etConfirmPassword=(EditText)findViewById(R.id.etConfirmPassword);
        btnChange = (Button)findViewById(R.id.btnChnage);
        cd = new ConnectionDetector(this);
        pd=new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait....");
        sd = new SessionManagment(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change Password");


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = etPassword.getText().toString();
                newpassword = etNewPassword.getText().toString();
                confirmPassword = etConfirmPassword.getText().toString();

                if(password.equals("")){
                    Toast.makeText(ResetPassword.this, "Please Enter Current Password", Toast.LENGTH_SHORT).show();
                }else if(newpassword.equals("")){
                    Toast.makeText(ResetPassword.this, "Please Enter New Password", Toast.LENGTH_SHORT).show();
                }else if(confirmPassword.equals("")){
                    Toast.makeText(ResetPassword.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }else if(password.equals(newpassword)){
                    Toast.makeText(ResetPassword.this, "Your current and new password is same", Toast.LENGTH_SHORT).show();
                }else if(!newpassword.equals(confirmPassword)){
                    Toast.makeText(ResetPassword.this, "Your Password does not match", Toast.LENGTH_SHORT).show();
                } else{
               changePassword(sd.getKEY_ID(),password,newpassword);
                }

            }
        });

    }


    public void changePassword(String a,String b, String c) {
        pd.show();
        JsonObject object=new JsonObject();
        object.addProperty("user_id",a);
        object.addProperty("password",b);
        object.addProperty("newpassword",c);
        Call<Login_model> call = apiInterface.getResetPassword(object);

        call.enqueue(new Callback<Login_model>() {
            @Override
            public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {

                try {
                    Login_model resource = response.body();
                    pd.dismiss();
                    if (resource.status.equals("success")) {
                        Intent in = new Intent(ResetPassword.this, MainActivity.class);
                        startActivity(in);
                        Toast.makeText(ResetPassword.this, "Your Password has updated Sucessfully..", Toast.LENGTH_SHORT).show();
                    } else if (resource.status.equals("passwordIncorrect")) {
                        Toast.makeText(ResetPassword.this, "Password is incorrect", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ResetPassword.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    pd.dismiss();
                    Toast.makeText(ResetPassword.this, "Please check your internet connection..", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Login_model> call, Throwable t) {
                call.cancel();
                pd.dismiss();
                Toast.makeText(ResetPassword.this, "Please try again..", Toast.LENGTH_SHORT).show();
            }
        });
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

}
