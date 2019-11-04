package com.ziasy.vaishnavvivah.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.adapter.GridAdapter;
import com.ziasy.vaishnavvivah.adapter.NewAllAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.ImageModel;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class AllImageActivity extends AppCompatActivity {

    TextView tv1;
    CircleImageView circleImageView ;
    GridView gridView ;
    SessionManagment sd;
    ConnectionDetector cd;
    APIInterface apiInterface ;
    ProgressDialog pd ;
    String encImage="";
    NewAllAdapter newAllAdapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);

        tv1 = (TextView)findViewById(R.id.imgUpload);
        circleImageView = (CircleImageView)findViewById(R.id.profile_image);
        gridView = (GridView)findViewById(R.id.Gridview);

        sd = new SessionManagment(this);
        cd = new ConnectionDetector(this);
        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Gallery");

        circleImageView.setVisibility(View.GONE);
        tv1.setVisibility(View.GONE);

        Intent in = getIntent();
        view(in.getStringExtra("id"));
    }

    public void view(String key) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(AllImageActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
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
                            newAllAdapter = new NewAllAdapter(AllImageActivity.this, resource.user);
                            gridView.setAdapter(newAllAdapter);
                        }else{
                            Toast.makeText(AllImageActivity.this, "No image uploaded by you", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        pd.dismiss();
                        Toast.makeText(AllImageActivity.this, "please try again..!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ImageModel> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(AllImageActivity.this, "please try again...!", Toast.LENGTH_SHORT).show();
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
