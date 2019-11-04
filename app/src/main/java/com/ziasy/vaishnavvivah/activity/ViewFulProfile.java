package com.ziasy.vaishnavvivah.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.R;

public class ViewFulProfile extends AppCompatActivity {

    TextView tvName,tvAge,tvGender,tvDob,tvGotra,tvFather,tvMother,tvFatherOccu,tvMotherOccu,
            tvEducation,tvOccupation,tvIncome,tvMobile,tvEmail,tvAddress,tvCity,tvState,tvStatus,tvImage;
    ImageView imageView ;
    String imgpath= "http://joietouch.com/matrimoney/images/";
    String id ="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Image");

        tvName=(TextView)findViewById(R.id.tvName);
        tvAge=(TextView)findViewById(R.id.tvAge);
        tvGender=(TextView)findViewById(R.id.tvGender);
        tvDob=(TextView)findViewById(R.id.tvDob);
        tvGotra=(TextView)findViewById(R.id.tvGotra);
        tvFather=(TextView)findViewById(R.id.tvFathrer);
        tvMother=(TextView)findViewById(R.id.tvMother);
        tvFatherOccu=(TextView)findViewById(R.id.tvFatherOccu);
        tvMotherOccu=(TextView)findViewById(R.id.tvMotherOccu);
        tvEducation=(TextView)findViewById(R.id.tvEducation);
        tvOccupation=(TextView)findViewById(R.id.tvOccupation);
        tvIncome=(TextView)findViewById(R.id.tvIncome);
        tvMobile=(TextView)findViewById(R.id.tvMobile);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvAddress=(TextView)findViewById(R.id.tvAddress);
        tvCity=(TextView)findViewById(R.id.tvCity);
        tvState=(TextView)findViewById(R.id.tvState);
        tvStatus=(TextView)findViewById(R.id.tvStatus);
        tvImage=(TextView)findViewById(R.id.allImg);
        imageView=(ImageView)findViewById(R.id.image);

        Intent in = getIntent();
        id = in.getStringExtra("id");
        tvName.setText(in.getStringExtra("name"));
        tvAge.setText(in.getStringExtra("age"));
        tvGender.setText(in.getStringExtra("gender"));
        tvDob.setText(in.getStringExtra("dob"));
        tvGotra.setText(in.getStringExtra("gotra"));
        tvStatus.setText(in.getStringExtra("status"));
        tvFather.setText(in.getStringExtra("father"));
        tvMother.setText(in.getStringExtra("mother"));
        tvFatherOccu.setText(in.getStringExtra("fatherocc"));
        tvMotherOccu.setText(in.getStringExtra("motherocc"));
        tvEducation.setText(in.getStringExtra("education"));
        tvOccupation.setText(in.getStringExtra("occupation"));
        tvIncome.setText(in.getStringExtra("income"));
        tvMobile.setText(in.getStringExtra("mobile"));
        tvEmail.setText(in.getStringExtra("email"));
        tvAddress.setText(in.getStringExtra("address"));
        tvCity.setText(in.getStringExtra("city"));
        tvState.setText(in.getStringExtra("state"));


        tvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ViewFulProfile.this,AllImageActivity.class);
                in.putExtra("id",id);
                startActivity(in);
            }
        });

         if(in.getStringExtra("profile").equals("dummy.png")){
           imageView.setImageResource(R.drawable.man);
         }else{
          Picasso.with(ViewFulProfile.this).load(imgpath+in.getStringExtra("profile")).into(imageView);
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
}
