package com.ziasy.vaishnavvivah.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.adapter.swipeAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.ImageModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class swipeActivity extends AppCompatActivity {

    APIInterface apiInterface;
    ProgressDialog pd ;
    ConnectionDetector cd;
    ViewPager viewPager;
    swipeAdapter swipeAdaptere ;
    String id = "",image="";
    int pos = 0;
   static ArrayList<ImageModel.userdetail> imagelist;
    SessionManagment sd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliderlayout);
        viewPager = (ViewPager) findViewById(R.id.viewpagerslider);
        pd = new ProgressDialog(swipeActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait....");
        cd = new ConnectionDetector(swipeActivity.this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        sd = new SessionManagment(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imagelist=new ArrayList<>();
        getSupportActionBar().setTitle("Users Image");
       //getUpdate(sd.getKEY_ID());
        getUserImage(sd.getKEY_ID());

        Intent in = getIntent();
       // id =  in.getStringExtra("id");
        image =  in.getStringExtra("image");
        pos = Integer.parseInt(in.getStringExtra("pos"));


    }

    public void getUpdate(String id) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(swipeActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            JsonObject object =new JsonObject();
            object.addProperty("userid",id);
            Call<ImageModel> call = apiInterface.getImageUploadView(object)   ;
            call.enqueue(new Callback<ImageModel>() {
                @Override
                public void onResponse(Call<ImageModel> call, retrofit2.Response<ImageModel> response) {
                    pd.dismiss();
                    try {
                        ImageModel resource = response.body();
                        if (resource.status.equals("success") && resource.user.size()!=0) {
                            swipeAdaptere = new swipeAdapter(swipeActivity.this, resource.user);
                            viewPager.setAdapter(swipeAdaptere);
                            viewPager.setCurrentItem(pos);
                        }else{
                            Toast.makeText(swipeActivity.this, "No image uploaded by you", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        pd.dismiss();
                        Toast.makeText(swipeActivity.this, "please try again..!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ImageModel> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(swipeActivity.this, "please try again... !", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
        }


    public void getUserImage(String id) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(swipeActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            JsonObject object =new JsonObject();
            object.addProperty("userid",id);
            Call<ImageModel> call = apiInterface.getImageUploadView(object)   ;
            call.enqueue(new Callback<ImageModel>() {
                @Override
                public void onResponse(Call<ImageModel> call, retrofit2.Response<ImageModel> response) {
                    pd.dismiss();
                    try {
                        ImageModel resource = response.body();
                        imagelist.addAll(resource.user);
                        if (resource.status.equals("success") && resource.user.size()!=0) {
                            swipeAdaptere = new swipeAdapter(swipeActivity.this,imagelist);
                            viewPager.setAdapter(swipeAdaptere);
                            viewPager.setCurrentItem(pos);
                        }else if(resource.status.equals("noData")){
                            Toast.makeText(swipeActivity.this, "No image exists", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                        pd.dismiss();
                    }

                }
                @Override
                public void onFailure(Call<ImageModel> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(swipeActivity.this, "please try again... !", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
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

