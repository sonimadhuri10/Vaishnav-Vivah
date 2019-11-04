package com.ziasy.vaishnavvivah.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.activity.ViewFulProfile;
import com.ziasy.vaishnavvivah.activity.checksum;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.Login_model;

import java.util.ArrayList;
import java.util.zip.Checksum;

import retrofit2.Call;
import retrofit2.Callback;

public class dashAdapter extends ArrayAdapter {

    ArrayList<Login_model.userdetail> al = null;
    Context context;
    int year =0,age=0;
    SessionManagment sd;
    ConnectionDetector cd;
    APIInterface apiInterface ;
    ProgressDialog pd ;

    public dashAdapter(Context context, ArrayList<Login_model.userdetail> al,int year) {
        super(context, R.layout.dash_items, al);
        this.context=context;
        this.al = al;
        this.year = year;
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
        final Login_model.userdetail lm = al.get(pos);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dash_items, parent, false);
            vh.tvName=(TextView)convertView.findViewById(R.id.tvName);
            vh.tvView=(TextView)convertView.findViewById(R.id.tvView);
            vh.tvLike=(TextView)convertView.findViewById(R.id.tvlike);
            vh.icon=(ImageView)convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        } else {
            vh =(ViewHolder)convertView.getTag();
        }

        vh.tvName.setText(lm.user_name);
        age = year - Integer.parseInt(lm.dob.substring(0,4));
        vh.tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                age = year - Integer.parseInt(lm.dob.substring(0,4));
                Intent in = new Intent(context, ViewFulProfile.class);

                in.putExtra("id",lm.id);
                in.putExtra("name",lm.user_name);
                in.putExtra("gotra",lm.gotra);
                in.putExtra("gender",lm.gender);
                in.putExtra("status",lm.marital_status);
                in.putExtra("age",String.valueOf(age)+" "+"Years");
                in.putExtra("dob",lm.dob.substring(0,10));
                in.putExtra("father",lm.father_name);
                in.putExtra("mother",lm.mother_name);
                in.putExtra("fatherocc",lm.father_occupation);
                in.putExtra("motherocc",lm.mother_occupation);
                in.putExtra("education",lm.education);
                in.putExtra("occupation",lm.occupation);
                in.putExtra("income",lm.income);
                in.putExtra("mobile",lm.user_mobile);
                in.putExtra("email",lm.user_email);
                in.putExtra("address",lm.address);
                in.putExtra("city",lm.city);
                in.putExtra("state",lm.state);
                in.putExtra("profile",lm.profile_pic);
                context.startActivity(in);

               /* if(sd.getpAYMENT_STATUS().equals("false")){
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
                }else{
                    age = year - Integer.parseInt(lm.dob.substring(0,4));
                    Intent in = new Intent(context, ViewFulProfile.class);
                    in.putExtra("name",lm.user_name);
                    in.putExtra("gotra",lm.gotra);
                    in.putExtra("gender",lm.gender);
                    in.putExtra("status",lm.marital_status);
                    in.putExtra("age",String.valueOf(age)+" "+"Years");
                    in.putExtra("dob",lm.dob.substring(0,10));
                    in.putExtra("father",lm.father_name);
                    in.putExtra("mother",lm.mother_name);
                    in.putExtra("fatherocc",lm.father_occupation);
                    in.putExtra("motherocc",lm.mother_occupation);
                    in.putExtra("education",lm.education);
                    in.putExtra("occupation",lm.occupation);
                    in.putExtra("income",lm.income);
                    in.putExtra("mobile",lm.user_mobile);
                    in.putExtra("email",lm.user_email);
                    in.putExtra("address",lm.address);
                    in.putExtra("city",lm.city);
                    in.putExtra("state",lm.state);
                    context.startActivity(in);
                }
*/
            }
        });

        vh.tvLike.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
             showIntereast(sd.getKEY_ID(),lm.id);
            }
        });

        //if(lm.profile_pic.equals("dummy.png")){
          //  vh.icon.setBackgroundResource(R.drawable.man);
        //}else{
            Picasso.with(context).load("http://joietouch.com/matrimoney/images/"+lm.profile_pic).error(R.drawable.man).into(vh.icon);
       // }


        if(lm.intrest.equals("Yes")){
            vh.tvLike.setText("Liked");
            vh.tvLike.setBackgroundColor(Color.RED);
        }else{
            vh.tvLike.setText("Like");
            vh.tvLike.setBackgroundColor(Color.parseColor("#f27072"));

        }


        return convertView;
    }

    public void showIntereast(String a,String b) {
        pd.show();
        JsonObject object=new JsonObject();
        object.addProperty("interest_off",a);
        object.addProperty("interest_in",b);
        Call<Login_model> call = apiInterface.getLike(object);

        call.enqueue(new Callback<Login_model>() {
            @Override
            public void onResponse(Call<Login_model> call, retrofit2.Response<Login_model> response) {
                try {
                    Login_model resource = response.body();
                    pd.dismiss();
                    if (resource.status.equals("success")) {
                        Toast.makeText(context, "your request has sent Sucessfully..", Toast.LENGTH_SHORT).show();
                    } else if (resource.status.equals("already shown interest in this profile")) {
                        Toast.makeText(context, "your request has already sent on this profile..", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Something went wrong..", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    pd.dismiss();
                    Toast.makeText(context, "please try again..!...", Toast.LENGTH_SHORT).show();

                }
            }
            @Override
            public void onFailure(Call<Login_model> call, Throwable t) {
                call.cancel();
                pd.dismiss();
                Toast.makeText(context, "Please try again..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    class ViewHolder {
        ImageView icon;
        TextView tvName ,tvView,tvLike;
    }

}