package com.ziasy.vaishnavvivah.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.activity.ViewFulProfile;
import com.ziasy.vaishnavvivah.activity.checksum;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.ArrayList;

public class SearchAdapter extends ArrayAdapter {

    ArrayList<Login_model.userdetail> al = null;
    Context context;
    int year =0,age=0;
    SessionManagment sd ;


    public SearchAdapter(Context context, ArrayList<Login_model.userdetail> al,int year) {
        super(context, R.layout.filter_items, al);
        this.context=context;
        this.al = al;
        this.year = year;
        sd = new SessionManagment(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();
        int pos = position;
        final Login_model.userdetail lm = al.get(pos);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.filter_items, parent, false);
            vh.tvName=(TextView)convertView.findViewById(R.id.tvName);
            vh.tvAge=(TextView)convertView.findViewById(R.id.tvAge);
            vh.tvDob=(TextView)convertView.findViewById(R.id.tvDob);
            vh.tvGender=(TextView)convertView.findViewById(R.id.tvGender);
            vh.tvGotra=(TextView)convertView.findViewById(R.id.tvGotra);
            vh.tvStatus=(TextView)convertView.findViewById(R.id.tvStatus);
            vh.tvView=(TextView)convertView.findViewById(R.id.tvView);
            vh.icon=(ImageView)convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        } else {
            vh =(ViewHolder)convertView.getTag();
        }

        vh.tvName.setText(lm.user_name);
        vh.tvGender.setText(lm.gender);
        vh.tvDob.setText(lm.dob.substring(0,10));
        vh.tvGotra.setText(lm.gotra);
        vh.tvStatus.setText(lm.marital_status);
        vh.tvName.setText(lm.user_name);
        age = year - Integer.parseInt(lm.dob.substring(0,4));
        vh.tvAge.setText(String.valueOf(age)+" "+"Years");

            vh.tvView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    age = year - Integer.parseInt(lm.dob.substring(0, 4));

                    Intent in = new Intent(context, ViewFulProfile.class);
                    in.putExtra("id",lm.id);
                    in.putExtra("name", lm.user_name);
                    in.putExtra("gotra", lm.gotra);
                    in.putExtra("gender", lm.gender);
                    in.putExtra("status", lm.marital_status);
                    in.putExtra("age", String.valueOf(age) + " " + "Years");
                    in.putExtra("dob", lm.dob.substring(0, 10));
                    in.putExtra("father", "Mr. Shyam Soni");
                    in.putExtra("mother", "Mrs. Babita Soni");
                    in.putExtra("fatherocc", "Business");
                    in.putExtra("motherocc", "Home Maker");
                    in.putExtra("education", lm.education);
                    in.putExtra("occupation", lm.occupation);
                    in.putExtra("income", "2 Lac");
                    in.putExtra("mobile", lm.user_mobile);
                    in.putExtra("email", lm.user_email);
                    in.putExtra("address", "10,Agrasen nagar");
                    in.putExtra("city", "Indore");
                    in.putExtra("state", "M.P.");
                    in.putExtra("profile",lm.profile_pic);
                    context.startActivity(in);

                  /*  if(sd.getpAYMENT_STATUS().equals("false")){
                        new android.app.AlertDialog.Builder(context)
                                .setTitle("Payment Status")
                                .setMessage("Sorry, You are unpaid user so you cant view full profile. For view of complete details please do payment. For payment cilck on Ok button")
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent inpay = new Intent(context, checksum.class);
                                                context.startActivity(inpay);
                                            }

                                        }).setNegativeButton("No", null).show();
                    }else {


                        age = year - Integer.parseInt(lm.dob.substring(0, 4));

                        Intent in = new Intent(context, ViewFulProfile.class);
                        in.putExtra("name", lm.user_name);
                        in.putExtra("gotra", lm.gotra);
                        in.putExtra("gender", lm.gender);
                        in.putExtra("status", lm.marital_status);
                        in.putExtra("age", String.valueOf(age) + " " + "Years");
                        in.putExtra("dob", lm.dob.substring(0, 10));
                        in.putExtra("father", "Mr. Shyam Soni");
                        in.putExtra("mother", "Mrs. Babita Soni");
                        in.putExtra("fatherocc", "Business");
                        in.putExtra("motherocc", "Home Maker");
                        in.putExtra("education", lm.education);
                        in.putExtra("occupation", lm.occupation);
                        in.putExtra("income", "2 Lac");
                        in.putExtra("mobile", lm.user_mobile);
                        in.putExtra("email", lm.user_email);
                        in.putExtra("address", "10,Agrasen nagar");
                        in.putExtra("city", "Indore");
                        in.putExtra("state", "M.P.");
                        context.startActivity(in);
                    }*/

                }
            });


        if(lm.profile_pic.equals("dummy.png")){
            vh.icon.setBackgroundResource(R.drawable.man);

        }else{
            Picasso.with(context).load("http://joietouch.com/matrimoney/images/"+lm.profile_pic).into(vh.icon);

        }
        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView tvName, tvAge,tvDob,tvGotra,tvGender,tvView,tvStatus;
    }


}