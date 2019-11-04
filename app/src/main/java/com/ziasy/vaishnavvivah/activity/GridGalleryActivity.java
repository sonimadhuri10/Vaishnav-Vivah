package com.ziasy.vaishnavvivah.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.Interface.DeletInterface;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.adapter.GridAdapter;
import com.ziasy.vaishnavvivah.adapter.dashAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.ImageModel;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class GridGalleryActivity extends AppCompatActivity implements View.OnClickListener, DeletInterface {

    TextView tv1;
    CircleImageView circleImageView ;
    GridView gridView ;
    SessionManagment sd;
    ConnectionDetector cd;
    APIInterface apiInterface ;
    ProgressDialog pd ;
    String encImage="";
    GridAdapter gridAdapter ;

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

        view(sd.getKEY_ID());
        circleImageView.setOnClickListener(this);
        tv1.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.imgUpload:
             circleImageView.setVisibility(View.VISIBLE);
             gridView.setVisibility(View.GONE);
             break;
         case R.id.profile_image:
             Intent i = new Intent(
                     Intent.ACTION_PICK,
                     android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
             startActivityForResult(i, 1);
             break;
     }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == 1 && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                // Picasso.with(UserSubscription.this).load(picturePath).into(circleImageView);
                circleImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                sd.setIMAGEPATH(picturePath);
                //Toast.makeText(UserSubscription.this,picturePath,Toast.LENGTH_SHORT).show();

                File imagefile = new File(picturePath);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(imagefile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bm = BitmapFactory.decodeStream(fis);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] b = baos.toByteArray();
                encImage = Base64.encodeToString(b, Base64.DEFAULT);

                //Toast.makeText(UserSubscription.this, encImage, Toast.LENGTH_SHORT).show();
                if(!encImage.isEmpty()){
                    uploadimage(sd.getKEY_ID(), encImage);
                }
            }

        } catch (Exception e) {
            Toast.makeText(GridGalleryActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
         }

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
                    circleImageView.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                    Toast.makeText(GridGalleryActivity.this, " Your image has Successfully Uploded ", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(GridGalleryActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
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
                            gridAdapter = new GridAdapter(GridGalleryActivity.this, resource.user);
                            gridView.setAdapter(gridAdapter);
                        }else{
                            Toast.makeText(GridGalleryActivity.this, "No image uploaded by you", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        pd.dismiss();
                        Toast.makeText(GridGalleryActivity.this, "please try again..!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ImageModel> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(GridGalleryActivity.this, "please try again... !", Toast.LENGTH_SHORT).show();
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


    @Override
    public void DeletInterface(String id) {
     view(id);
    }
}
