package com.ziasy.vaishnavvivah.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.ziasy.vaishnavvivah.model.OtpModel;

import retrofit2.Call;
import retrofit2.Callback;

public class OtpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etOtp;
    Button btnSubmuit, btnResend ;
    String otp="",insertotp="",profilefor="",gender="",mother="";
    ConnectionDetector cd;
    ProgressDialog pd ;
    APIInterface apiInterface ;
    SessionManagment sd ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_layout);

        getSupportActionBar().setTitle("Otp Varification");
        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(this);
        sd = new SessionManagment(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait.....");

        etOtp = (EditText)findViewById(R.id.etOtp);
        btnSubmuit = (Button) findViewById(R.id.btnSubmit);
        btnResend = (Button) findViewById(R.id.btnResend);

        btnSubmuit.setOnClickListener(this);
        btnResend.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSubmit:
                Intent in =getIntent();
                otp = in.getStringExtra("otp");
                profilefor = in.getStringExtra("profile");
                gender = in.getStringExtra("gender");
                mother = in.getStringExtra("mother");

                insertotp = etOtp.getText().toString();

                if(profilefor.equals("undefined") && gender.equals("undefined") && mother.equals("undefined")){
                    Intent inh = new Intent(OtpActivity.this, UserSubscription.class);
                    startActivity(inh);
                    finish();
                 } else  if(insertotp.equals(otp)){
                    sd.setLOGIN_STATUS("true");
                    sd.setGENDER(gender);
                    sd.setLOGOUT_STATUS("false");
                    Toast.makeText(OtpActivity.this,"Otp Verification Successfully",Toast.LENGTH_SHORT).show();
                    Intent ing = new Intent(OtpActivity.this, MainActivity.class);
                    startActivity(ing);
                    finish();
                }else{
                    Toast.makeText(OtpActivity.this,"Wrong otp",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnResend:
                 resendOtp(sd.getKEY_ID());
                break;
        }
    }


    public void resendOtp(String a) {
        pd.show();
        JsonObject object=new JsonObject();
        object.addProperty("user_id",a);
        Call<OtpModel> call = apiInterface.getResendOtp(object);
        call.enqueue(new Callback<OtpModel>() {
            @Override
            public void onResponse(Call<OtpModel> call, retrofit2.Response<OtpModel> response) {
                try {
                    OtpModel resource = response.body();
                    pd.dismiss();
                    if (resource.status.equals("success")) {
                        Toast.makeText(OtpActivity.this, "Send Sucessfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(OtpActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    pd.dismiss();
                    Toast.makeText(OtpActivity.this, "Please check your internet connection..", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<OtpModel> call, Throwable t) {
                call.cancel();
                pd.dismiss();
                Toast.makeText(OtpActivity.this, "Please try again..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
