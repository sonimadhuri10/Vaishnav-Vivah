package com.ziasy.vaishnavvivah.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.activity.Forgot_password;
import com.ziasy.vaishnavvivah.adapter.SearchAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class Search extends Fragment {

     ListView listView;
     SearchView searchView ;
     ConnectionDetector cd;
     SessionManagment sd;
     ProgressDialog pd ;
     APIInterface apiInterface ;
     SearchAdapter adapter;
     int year=0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.search_layout,container,false);


        listView=(ListView)v.findViewById(R.id.searchList);
        cd = new ConnectionDetector(getActivity());
        sd = new SessionManagment(getActivity());
        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        add(sd.getGENDER(),sd.getKEY_ID());


        return  v;
    }


    public void add(String Gender,String id){
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getActivity(),"Please check your internet connection...",Toast.LENGTH_SHORT).show();
        }else {

            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("gender",Gender);
            object.addProperty("userid",id);
            Call<Login_model> call = apiInterface.getProfiles(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    try {
                        pd.dismiss();
                        year = Calendar.getInstance().get(Calendar.YEAR);
                        Login_model resource = response.body();
                        adapter = new SearchAdapter(getActivity(), resource.user, year);
                        listView.setAdapter(adapter);
                    }catch (Exception e){
                        pd.dismiss();
                        Toast.makeText(getActivity(), "please try again..!...", Toast.LENGTH_SHORT).show();
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

    public void retrofilter(String fil){
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(getActivity(),"Please check your internet connection...",Toast.LENGTH_SHORT).show();
        }else {
            pd.show();
            JsonObject object=new JsonObject();
            object.addProperty("search_name",fil);
            Call<Login_model> call = apiInterface.getSearch(object);
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pd.dismiss();
                    year = Calendar.getInstance().get(Calendar.YEAR);
                    Login_model resource = response.body();
                    adapter = new SearchAdapter(getActivity(), resource.user,year);
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
}
