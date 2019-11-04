package com.ziasy.vaishnavvivah.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.adapter.SearchAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;

public class NotificationActivity extends AppCompatActivity {

    ListView listView;
    EditText etSearch;
    ImageView imgGo;
    ConnectionDetector cd;
    SessionManagment sd;
    ProgressDialog pd ;
    APIInterface apiInterface ;
    SearchAdapter adapter;
    int year=0;
    String search="";
    RelativeLayout rrHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        listView=(ListView)findViewById(R.id.searchList);
        etSearch = (EditText)findViewById(R.id.etSearch);
        imgGo = (ImageView)findViewById(R.id.imgGo);
        rrHeader=(RelativeLayout)findViewById(R.id.header);
        cd = new ConnectionDetector(NotificationActivity.this);
        sd = new SessionManagment(NotificationActivity.this);
        pd = new ProgressDialog(NotificationActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Requests");

        rrHeader.setVisibility(View.GONE);
        add(sd.getKEY_ID());

    }

    public void add(String id){
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(NotificationActivity.this,"Please check your internet connection...",Toast.LENGTH_SHORT).show();
        }else {
            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("userid",id);
            Call<Login_model> call = apiInterface.getInteresrIn(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pd.dismiss();
                    try {
                        year = Calendar.getInstance().get(Calendar.YEAR);
                        Login_model resource = response.body();

                        if (resource.user.size() != 0) {
                            adapter = new SearchAdapter(NotificationActivity.this, resource.user, year);
                            listView.setAdapter(adapter);
                        } else {
                            Toast.makeText(NotificationActivity.this, "No Any request Are Found", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        pd.dismiss();
                        Toast.makeText(NotificationActivity.this,"Please check your internet connection..",Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
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
