package com.ziasy.vaishnavvivah.adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.ziasy.vaishnavvivah.activity.ViewImageActivity;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.ImageModel;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class NewAllAdapter extends ArrayAdapter {

    ArrayList<ImageModel.userdetail> al = null;
    Context context;


    public NewAllAdapter(Context context, ArrayList<ImageModel.userdetail> al) {
        super(context, R.layout.grid_items, al);
        this.context = context;
        this.al = al;

    }

    @SuppressLint("WrongViewCast")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();
        int pos = position;
        final ImageModel.userdetail lm = al.get(pos);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.grid_items, parent, false);
           // vh.tvDelet = (TextView) convertView.findViewById(R.id.tvDelet);
          //  vh.tvview = (TextView) convertView.findViewById(R.id.tvView);
            vh.icon = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

      //  vh.tvDelet.setVisibility(View.GONE);
      //  vh.tvview.setVisibility(View.GONE);
        Picasso.with(context).load("http://joietouch.com/matrimoney/images/"+lm.img_name).error(R.drawable.man).into(vh.icon);

        vh.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(context,ViewImageActivity.class);
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


}


