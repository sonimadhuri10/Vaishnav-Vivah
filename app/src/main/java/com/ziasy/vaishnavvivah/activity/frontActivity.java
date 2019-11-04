package com.ziasy.vaishnavvivah.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.ziasy.vaishnavvivah.R;

public class frontActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tvRegister ,tvLogin ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frontlayout);

        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvLogin = (TextView) findViewById(R.id.tvlogin);
        tvLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        permission();
    }

    @Override
    public void onClick(View view) {
      switch (view.getId()){
          case R.id.tvRegister :
              Intent in = new Intent(frontActivity.this,signupActivity.class);
              startActivity(in);
             // finish();
              break;
          case R.id.tvlogin :
              Intent in1 = new Intent(frontActivity.this,loginActivity.class);
              startActivity(in1);
             // finish();
              break;
      }
    }

    public void permission() {
        if (Build.VERSION.SDK_INT < 23) {
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED)
             {
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.RECEIVE_SMS},
                        3);
            } else {
            }
        }
    }
}
