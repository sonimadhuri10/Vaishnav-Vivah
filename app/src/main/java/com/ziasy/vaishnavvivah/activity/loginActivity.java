package com.ziasy.vaishnavvivah.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;
import com.ziasy.vaishnavvivah.model.NewLoginModel;

import retrofit2.Call;
import retrofit2.Callback;

public class loginActivity extends AppCompatActivity  implements View.OnClickListener {

    EditText etMobile,etpassword;
    TextView tvForgot,tvSignup ;
    Button btnLogin ;
    String mobile = "",password = "",user_id="";
    ConnectionDetector cd ;
    ProgressDialog pd ;
    APIInterface apiInterface;
    SessionManagment sessionManagment ;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginlayout);

        etMobile = (EditText)findViewById(R.id.etMobile);
        etpassword = (EditText)findViewById(R.id.etPassword);
        tvForgot = (TextView) findViewById(R.id.tvForgot);
        tvSignup = (TextView)findViewById(R.id.tvSignup);
        btnLogin = (Button) findViewById(R.id.btnlogin);

        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(loginActivity.this);
        sessionManagment = new SessionManagment(loginActivity.this);
        pd = new ProgressDialog(loginActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please Wait..!");

        tvForgot.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvSignup.setOnClickListener(this);

    }

    public  void validation(){
        mobile = etMobile.getText().toString();
        password = etpassword.getText().toString();

        if(mobile.isEmpty()){
            etMobile.setError("Please Enter Mobile No. or Email");
        }else if(mobile.matches("[0-9]+")&& mobile.length()!=10){
            etMobile.setError("Please Enter valid Mobile No.");
        }else if(mobile.matches("[a-zA-Z]+")&& !mobile.matches(emailPattern)){
            etMobile.setError("Please Enter valid Email");
        } else if(password.isEmpty()){
            etpassword.setError("Please Enter password");
        }else if(password.length()<6){
            etpassword.setError("Please Enter Minimum 6 Character Password");

        }else{
         login(mobile,password,sessionManagment.getFCM());
        }

    }

    public void login(String mob, String pass,String dev) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(loginActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("user_mobile",mob);
            object.addProperty("user_password",pass);
            object.addProperty("device_id",dev);

            Call<NewLoginModel> call = apiInterface.postLogin(object);
            call.enqueue(new Callback<NewLoginModel>() {
                @Override
                public void onResponse(Call<NewLoginModel> call, retrofit2.Response<NewLoginModel> response) {
                    pd.dismiss();
                  try {
                      NewLoginModel resource = response.body();
                      if (resource.status.equals("user not registered")) {
                          Toast.makeText(loginActivity.this, "User Not Register", Toast.LENGTH_SHORT).show();

                      }else
                     if (resource.status.equals("success")) {
                          if (resource.user.get(0).gender.equals("undefined") && resource.user.get(0).profile_for.equals("undefined")) {
                              user_id = resource.user.get(0).id;
                              sessionManagment.setKEY_ID(user_id);
                              Intent inh = new Intent(loginActivity.this, UserSubscription.class);
                              startActivity(inh);
                              finish();
                          } else {
                              user_id = resource.user.get(0).id;
                              sessionManagment.setKEY_ID(user_id);
                              sessionManagment.setLOGIN_STATUS("true");
                              sessionManagment.setGENDER(resource.user.get(0).gender);
                              sessionManagment.setMOBILE(resource.user.get(0).user_mobile);
                              sessionManagment.setEMAIL(resource.user.get(0).user_email);
                              sessionManagment.setLOGOUT_STATUS("false");
                              Intent ing = new Intent(loginActivity.this, MainActivity.class);
                              startActivity(ing);
                              finish();
                          }
                      } else if (resource.status.equals("invalide password")) {
                         pd.dismiss();

                         Toast.makeText(loginActivity.this, "Password is Incorrect", Toast.LENGTH_SHORT).show();
                      } else {
                         pd.dismiss();

                         Toast.makeText(loginActivity.this, "Something went wrong...", Toast.LENGTH_SHORT).show();
                      }

                  }catch (Exception e){
                      pd.dismiss();
                      Toast.makeText(loginActivity.this, "please check your internet connection", Toast.LENGTH_SHORT).show();
                  }
                    }

                @Override
                public void onFailure(Call<NewLoginModel> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(loginActivity.this, "please try again... !", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvForgot :
                Intent inf = new Intent(loginActivity.this,Forgot.class);
                startActivity(inf);
                break;
                case R.id.tvSignup :
                Intent in = new Intent(loginActivity.this,signupActivity.class);
                startActivity(in);
                finish();
                break;
                case R.id.btnlogin :
                validation();
                break;
        }
    }


}
