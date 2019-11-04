package com.ziasy.vaishnavvivah.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.adapter.SearchAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.fragment.Filter;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity  implements View.OnClickListener {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);

        listView=(ListView)findViewById(R.id.searchList);
        etSearch = (EditText)findViewById(R.id.etSearch);
        imgGo = (ImageView)findViewById(R.id.imgGo);
        cd = new ConnectionDetector(SearchActivity.this);
        sd = new SessionManagment(SearchActivity.this);
        pd = new ProgressDialog(SearchActivity.this);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search profiles");

        add(sd.getGENDER(),sd.getKEY_ID());
        imgGo.setOnClickListener(this);
    }


    public void add(String Gender, String id){
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(SearchActivity.this,"Please check your internet connection...",Toast.LENGTH_SHORT).show();
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
                    adapter = new SearchAdapter(SearchActivity.this, resource.user,year);
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
            Toast.makeText(SearchActivity.this,"Please check your internet connection...",Toast.LENGTH_SHORT).show();
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
                            adapter = new SearchAdapter(SearchActivity.this, resource.user, year);
                            listView.setAdapter(adapter);
                        } else {
                            hideSoftKeyboard();
                            listView.setVisibility(View.GONE);
                            Toast.makeText(SearchActivity.this, "No profile according to this filter...", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        pd.dismiss();
                        Toast.makeText(SearchActivity.this, "please try again..!...", Toast.LENGTH_SHORT).show();

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
         case R.id.imgGo:
             search = etSearch.getText().toString();
             if(search.equals("")){
                 Toast.makeText(SearchActivity.this, "Please enter input for search", Toast.LENGTH_SHORT).show();
             }else{
                 retrofilter(search);
             }
             break;
     }
    }
}
