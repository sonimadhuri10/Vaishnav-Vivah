package com.ziasy.vaishnavvivah.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.comman.SessionManagment;

public class splashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    SessionManagment sd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashlayout);
        sd = new SessionManagment(this);

        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
            @Override
            public void run() {
                if(sd.getLOGIN_STATUS().equals("true")){
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(getApplicationContext(), frontActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, SPLASH_TIME_OUT);
    }
}
