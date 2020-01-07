/*
package com.ziasy.vaishnavvivah.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivitytwo extends AppCompatActivity  implements View.OnClickListener {

    ListView listView;
    TextView etSearch;
    ImageView imgGo1;
    ConnectionDetector cd;
    SessionManagment sd;
    ProgressDialog pd ;
    APIInterface apiInterface ;

    int year=0;
    String search="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.searchList1);
        etSearch = (TextView) findViewById(R.id.etSearch1);
        imgGo1 = (ImageView)findViewById(R.id.imgGo1);
        cd = new ConnectionDetector(SearchActivitytwo.this);
        sd = new SessionManagment(SearchActivitytwo.this);
        pd = new ProgressDialog(SearchActivitytwo.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search profiles");

        add(sd.getGENDER(),sd.getKEY_ID());
        imgGo1.setOnClickListener(this);
    }


    public void add(String Gender, String id){
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(SearchActivitytwo.this,"Please check your internet connection...",Toast.LENGTH_SHORT).show();
        }else {
            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("gender",Gender);
            object.addProperty("userid",id);
            Call<Login_model> call = apiInterface.getProfiles(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pd.dismiss();
                    year = Calendar.getInstance().get(Calendar.YEAR);
                    Login_model resource = response.body();
                    adapter = new SearchAdaptertwo(SearchActivitytwo.this, resource.user,year);
                    listView.setAdapter(adapter);
                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    pd.dismiss();
                }
            });
        }
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
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

    public void retrofilter(String fil){
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(SearchActivitytwo.this,"Please check your internet connection...",Toast.LENGTH_SHORT).show();
        }else {
            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("search_name",fil);
            Call<Login_model> call = apiInterface.getSearch(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pd.dismiss();
                    try {
                        year = Calendar.getInstance().get(Calendar.YEAR);
                        Login_model resource = response.body();

                        if (resource.status.equals("success")) {
                            listView.setVisibility(View.VISIBLE);
                            hideSoftKeyboard();
                            adapter = new SearchAdaptertwo(SearchActivitytwo.this, resource.user, year);
                            listView.setAdapter(adapter);
                        } else {
                            hideSoftKeyboard();
                            listView.setVisibility(View.GONE);
                            Toast.makeText(SearchActivitytwo.this, "No profile according to this filter...", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        pd.dismiss();
                        Toast.makeText(SearchActivitytwo.this, "please try again..!...", Toast.LENGTH_SHORT).show();

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

    @Override
    public void onClick(View view) {
     switch (view.getId()){
         case R.id.imgGo1:
             search = etSearch.getText().toString();
             if(search.equals("")){
                 Toast.makeText(SearchActivitytwo.this, "Please enter input for search", Toast.LENGTH_SHORT).show();
             }else{
                 retrofilter(search);
             }
             break;
     }
    }
}
*/
