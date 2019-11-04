package com.ziasy.vaishnavvivah.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.BuildConfig;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;
import com.ziasy.vaishnavvivah.model.Signup_model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;

public class signupActivity extends AppCompatActivity {

    EditText etName, etmobile, etpassword, etemail;
    Button btnSignup;
    String strName = "", strEmail = "", strPassword = "", strMobile = "",imei="",deviceid="";
    APIInterface apiInterface;
    TextView tvLogin;

    DatePickerDialog.OnDateSetListener date;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ConnectionDetector cd;
    SessionManagment sd;
    ProgressDialog pd;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signuplayout);

        btnSignup = (Button) findViewById(R.id.btnSignup);
        etName = (EditText) findViewById(R.id.etName);
        etmobile = (EditText) findViewById(R.id.etMobile);
        etemail = (EditText) findViewById(R.id.etEmail);
        etpassword = (EditText) findViewById(R.id.etPassword);
        tvLogin = (TextView) findViewById(R.id.tvLogin);


        cd = new ConnectionDetector(signupActivity.this);
        sd = new SessionManagment(signupActivity.this);
        pd = new ProgressDialog(signupActivity.this);
        pd.setMessage("please wait  ....");
        pd.setCancelable(false);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(signupActivity.this,loginActivity.class);
                startActivity(in);
                finish();
            }
        });
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    public void validation() {

        strName = etName.getText().toString();
        strEmail = etemail.getText().toString();
        strMobile = etmobile.getText().toString();
        strPassword = etpassword.getText().toString();


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
         imei = telephonyManager.getDeviceId();

       if(strName.isEmpty()){
            etName.setError("Please Enter Name");
        }else if(strEmail.isEmpty()){
            etemail.setError("Please Enter Email");
        }else if(!strEmail.matches(emailPattern)){
            etemail.setError("Please Enter Valid Email");
        }else if(strMobile.isEmpty()){
            etmobile.setError("Please Enter Mobile No.");
        }else if(strMobile.length()!=10){
            etmobile.setError("Please Enter Valid Mobile No.");
        }else if(strPassword.equals("")){
           etpassword.setError("Please Enter password");
       }else{
             signin(strName,strEmail,strMobile,strPassword,imei,sd.getFCM());
        }

    }

    public void signin(String nam, String em, String mob, String pas,String im, String dev) {
        pd.show();
        JsonObject object=new JsonObject();
        object.addProperty("name",nam);
        object.addProperty("email",em);
        object.addProperty("mobile",mob);
        object.addProperty("password",pas);
        object.addProperty("imei",im);
        object.addProperty("device_id",dev);


        Call<Signup_model> call = apiInterface.postData(object);
        call.enqueue(new Callback<Signup_model>() {
            @Override
            public void onResponse(Call<Signup_model> call, retrofit2.Response<Signup_model> response) {
                try {
                    Signup_model resource = response.body();
                    pd.dismiss();
                    if (resource.status.equals("success")) {
                        Intent in = new Intent(signupActivity.this, loginActivity.class);
                        startActivity(in);
                        Toast.makeText(signupActivity.this, "Signin Successfully", Toast.LENGTH_SHORT).show();
                    } else if (resource.status.equals("useralready")) {
                        Toast.makeText(signupActivity.this, "Your mobile No or email already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(signupActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    pd.dismiss();
                    Toast.makeText(signupActivity.this, "Please Check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Signup_model> call, Throwable t) {
               pd.dismiss();
                Toast.makeText(signupActivity.this, "Please Check your internet connection", Toast.LENGTH_SHORT).show();
                call.cancel();
            }
        });
    }



}
