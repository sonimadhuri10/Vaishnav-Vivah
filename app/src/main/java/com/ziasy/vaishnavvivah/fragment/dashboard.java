package com.ziasy.vaishnavvivah.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.activity.loginActivity;
import com.ziasy.vaishnavvivah.adapter.ItemArrayAdapter;
import com.ziasy.vaishnavvivah.adapter.dashAdapter;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.GridModel;
import com.ziasy.vaishnavvivah.model.ListModel;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class dashboard extends Fragment {

    GridView gridview ;
    ConnectionDetector cd;
    SessionManagment sd;
    ProgressDialog pd;
    ArrayList<GridModel> al = null;
    dashAdapter dashAdapter ;
    APIInterface apiInterface ;
    Calendar myCalendar;
    int year=0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.gridlayout, container, false);

        gridview = (GridView)v.findViewById(R.id.Gridview);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(getActivity());
        sd = new SessionManagment(getActivity());
        pd = new ProgressDialog(getActivity());
        pd.setCancelable(false);
        pd.setMessage("Please Wait..!");
        myCalendar = Calendar.getInstance();
        al =new ArrayList<>();

       add(sd.getGENDER(),sd.getKEY_ID());
     //  Toast.makeText(getActivity(),sd.getLOGOUT_STATUS(),Toast.LENGTH_SHORT).show();

        return v;
    }

  public  void add(String Gender,String id){
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
                          al.clear();

                          if (resource.status.equals("success")) {
                              dashAdapter = new dashAdapter(getActivity(), resource.user, year);
                              gridview.setAdapter(dashAdapter);
                          } else {
                              Toast.makeText(getActivity(), "No profile vailable....", Toast.LENGTH_SHORT).show();
                          }
                      }catch (Exception e){
                          pd.dismiss();
                          Toast.makeText(getActivity(),"Please checkyour internet connection",Toast.LENGTH_SHORT).show();
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

    public  void adddata(){

        GridModel l1 = new GridModel(R.drawable.man, "Abhi Soni","28 years");
        GridModel l2 = new GridModel(R.drawable.man, "Aaksh Singh","25 years");
        GridModel l3 = new GridModel(R.drawable.man, "Saumya Singh","26 years");
        GridModel l4 = new GridModel(R.drawable.man, "Dev Prabhat","27 years");
        GridModel l5 = new GridModel(R.drawable.man, "Sonu Mittal","25 years");
        GridModel l6 = new GridModel(R.drawable.man, "Kapil Soni","22 years");
        GridModel l7 = new GridModel(R.drawable.man, "Sanjay Pol","27 years");
        GridModel l8 = new GridModel(R.drawable.man, "Abhi Tak","28 years");

        al.add(l1);
        al.add(l2);
        al.add(l3);
        al.add(l4);
        al.add(l5);
        al.add(l6);
        al.add(l7);
        al.add(l8);
        //dashAdapter = new dashAdapter(getActivity(), al);
        //gridview.setAdapter(dashAdapter);
    }
}
