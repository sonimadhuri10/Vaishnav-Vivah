package com.ziasy.vaishnavvivah.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.activity.AllImageActivity;

public class FamilyDetailsfragment extends Fragment {
    TextView tvName,tvAge,tvGender,tvDob,tvGotra,tvFather,tvMother,tvFatherOccu,tvMotherOccu,
            tvEducation,tvOccupation,tvIncome,tvMobile,tvEmail,tvAddress,tvCity,tvState,tvStatus;
    ImageView imageView,tvImage ;
    String imgpath= "http://joietouch.com/matrimoney/images/";
    String id ="";

    // TODO: Rename parameter arguments, choose names that match


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*tvName.setText("name");
        tvAge.setText("age");
        tvGender.setText("gender");
        tvDob.setText("dob");
        tvGotra.setText("gotra");
        tvStatus.setText("status");
        tvFather.setText("father");
        tvMother.setText("mother");
        tvFatherOccu.setText("fatherocc");
        tvMotherOccu.setText("motherocc");
        tvEducation.setText("education");
        tvOccupation.setText("occupation");
        tvIncome.setText("income");
        tvMobile.setText("mobile");
        tvEmail.setText("email");
        tvAddress.setText("address");
        tvCity.setText("city");
        tvState.setText("state");
*/

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View con = inflater.inflate(R.layout.gridlayout, container, false);

        tvName = (TextView) con.findViewById(R.id.tvName);
        tvAge = (TextView) con.findViewById(R.id.tvAge);
        tvGender = (TextView) con.findViewById(R.id.tvGender);
        tvDob = (TextView) con.findViewById(R.id.tvDob);
        tvGotra = (TextView) con.findViewById(R.id.tvGotra);
        tvFather = (TextView) con.findViewById(R.id.tvFathrer);
        tvMother = (TextView) con.findViewById(R.id.tvMother);
        tvFatherOccu = (TextView) con.findViewById(R.id.tvFatherOccu);
        tvMotherOccu = (TextView) con.findViewById(R.id.tvMotherOccu);
        tvEducation = (TextView) con.findViewById(R.id.tvEducation);
        tvOccupation = (TextView) con.findViewById(R.id.tvOccupation);
        tvIncome = (TextView) con.findViewById(R.id.tvIncome);
        tvMobile = (TextView) con.findViewById(R.id.tvMobile);
        tvEmail = (TextView) con.findViewById(R.id.tvEmail);
        tvAddress = (TextView) con.findViewById(R.id.tvAddress);
        tvCity = (TextView) con.findViewById(R.id.tvCity);
        tvState = (TextView) con.findViewById(R.id.tvState);
        tvStatus = (TextView) con.findViewById(R.id.tvStatus);
        tvImage = (ImageView) con.findViewById(R.id.allImg);
        imageView = (ImageView) con.findViewById(R.id.image);

        Bundle args = new Bundle();



        return con;
    }




}
