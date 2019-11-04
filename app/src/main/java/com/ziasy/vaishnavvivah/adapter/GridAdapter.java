package com.ziasy.vaishnavvivah.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.Interface.DeletInterface;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.activity.GridGalleryActivity;
import com.ziasy.vaishnavvivah.activity.ViewFulProfile;
import com.ziasy.vaishnavvivah.activity.ViewImageActivity;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.ImageModel;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class GridAdapter extends ArrayAdapter {

    ArrayList<ImageModel.userdetail> al = null;
    Context context;
    SessionManagment sd;
    ConnectionDetector cd;
    APIInterface apiInterface;
    ProgressDialog pd;
    DeletInterface deletInterface;

    public GridAdapter(Context context, ArrayList<ImageModel.userdetail> al) {
        super(context, R.layout.grid_items, al);
        this.context = context;
        this.al = al;
        deletInterface = (DeletInterface) context;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(context);
        sd = new SessionManagment(context);
        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Please wait...");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();
        int pos = position;
        final ImageModel.userdetail lm = al.get(pos);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grid_items, parent, false);
            vh.tvDelet = (TextView) convertView.findViewById(R.id.tvDelet);
            vh.tvview = (TextView) convertView.findViewById(R.id.tvView);
            vh.icon = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Picasso.with(context).load("http://joietouch.com/matrimoney/images/"+lm.img_name).error(R.drawable.man).into(vh.icon);

        vh.tvDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    delet(sd.getKEY_ID(),lm.item_id);
            }
        });

        vh.tvview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context, ViewImageActivity.class);
                in.putExtra("image",lm.img_name);
                context.startActivity(in);
            }
        });


        return convertView;


    }

    class ViewHolder {
        ImageView icon;
        TextView tvDelet, tvview;
    }

    public void delet(String key, String itemid) {
        if (!cd.isConnectingToInternet()) {
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            pd.show();
            JsonObject object =new JsonObject();
            object.addProperty("user_id",key);
            object.addProperty("item_id",itemid);
            Call<Login_model> call = apiInterface.getDelet(object)   ;
            call.enqueue(new Callback<Login_model>() {
                @Override
                public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                    pd.dismiss();
                    try{
                        Login_model resource = response.body();
                        if(resource.status.equals("success")) {
                            Toast.makeText(context, "Successfully delete", Toast.LENGTH_SHORT).show();
                            deletInterface.DeletInterface(sd.getKEY_ID());
                        }
                    }catch (Exception e){
                        Toast.makeText(context, "please check your internet connection!", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onFailure(Call<Login_model> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(context, "please try again... !", Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                }
            });
        }
    }







}


