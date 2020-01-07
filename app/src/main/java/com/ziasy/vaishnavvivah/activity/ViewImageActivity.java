package com.ziasy.vaishnavvivah.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.adapter.GridAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.ImageModel;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class ViewImageActivity extends AppCompatActivity {
    ImageView img1;
    String imgpath= "http://joietouch.com/matrimoney/images/";
    ViewPager viewPager;
    SessionManagment sd ;
    ConnectionDetector cd;
    APIInterface apiInterface ;
    ProgressDialog pd ;
    CustomPagerAdapter viewAdapter ;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewimage_layout);
        sd = new SessionManagment(this);
        cd = new ConnectionDetector(this);
        pd = new ProgressDialog(this);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Full Profile");
       // apiInterface = (APIInterface) this;
        img1=(ImageView)findViewById(R.id.image);
        view(sd.getKEY_ID());
        viewPager = (ViewPager) findViewById(R.id.viewpagerslider);


        Intent in = getIntent();
        Picasso.with(ViewImageActivity.this).load(imgpath+in.getStringExtra("image")).into(img1);

        /*  ArrayList str = new ArrayList();
         str.add(imgpath+in.getStringExtra("image"));
        String[] photoUrls=new String[str.size()];

        int nCount = str.size();

        for (int i=0;i<=nCount;i++){


        }



        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpagerslider);

        if (viewPager != null) {
            viewPager.setAdapter(new CustomPagerAdapter(ViewImageActivity.this, photoUrls));
        }
*/
    }

    public void uploadimage (String id, String img){
        pd.show();
        JsonObject object=new JsonObject();
        object.addProperty("userid",id);
        object.addProperty("profile",img);
        Call<Login_model> call = apiInterface.getImageUpload(object);
        call.enqueue(new Callback<Login_model>() {
            @Override
            public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                pd.dismiss();
                Login_model resource = response.body();
                if(resource.status.equals("success")) {
                    // view(sd.getKEY_ID());
                    //Picasso.with(UserSubscription.this).load(imgPath+resource.status).into(circleImageView);
                    //circleImageView.setVisibility(View.GONE);
                    img1.setVisibility(View.VISIBLE);
                    Toast.makeText(ViewImageActivity.this, " Your image has Successfully Uploded ", Toast.LENGTH_SHORT).show();
                    view(sd.getKEY_ID());
                }
            }
            @Override
            public void onFailure(Call<Login_model> call, Throwable t) {
                call.cancel();
                pd.dismiss();
            }
        });
    }

    public void view(String key) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(ViewImageActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            JsonObject object =new JsonObject();
            object.addProperty("userid",key);
            Call<ImageModel> call = apiInterface.getImageUploadView(object)   ;
            call.enqueue(new Callback<ImageModel>() {
                @Override
                public void onResponse(Call<ImageModel> call, retrofit2.Response<ImageModel> response) {
                    pd.dismiss();
                    try {
                        ImageModel resource = response.body();
                        if (resource.status.equals("success") && resource.user.size()!=0) {
                            viewAdapter = new CustomPagerAdapter(ViewImageActivity.this, resource.user);
                            viewPager.setAdapter(viewAdapter);
                        }else{
                           // Toast.makeText(ViewImageActivity.this, "No image uploaded by you", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        pd.dismiss();
                        Toast.makeText(ViewImageActivity.this, "please try again..!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ImageModel> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(ViewImageActivity.this, "please try again... !", Toast.LENGTH_SHORT).show();
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
