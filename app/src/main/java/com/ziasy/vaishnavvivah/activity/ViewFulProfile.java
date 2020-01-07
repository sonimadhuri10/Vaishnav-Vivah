package com.ziasy.vaishnavvivah.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.R;

import com.ziasy.vaishnavvivah.fragment.FamilyDetailsfragment;
import com.ziasy.vaishnavvivah.fragment.UserDetailsfragment;

import java.util.ArrayList;
import java.util.List;

public class ViewFulProfile extends AppCompatActivity {

    TextView tvName,tvAge,tvGender,tvDob,tvGotra,tvFather,tvMother,tvFatherOccu,tvMotherOccu,
            tvEducation,tvOccupation,tvOccupationnew,tvIncome,tvMobile,tvEmail,tvAddress,tvCity,tvState,tvCity1,tvState1,tvStatus;
    ImageView imageView,tvImage ;
    Button about,family;
    CardView familycard,othercard,contactcard,addresscard;
    String imgpath= "http://joietouch.com/matrimoney/images/";
    String id ="";
     TabLayout tabLayout;
     ViewPager viewPager;
    UserDetailsfragment fu = new UserDetailsfragment();
    FamilyDetailsfragment fd = new FamilyDetailsfragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        about = findViewById(R.id.btnabout);
        family = findViewById(R.id.btnfamily);
        familycard = findViewById(R.id.cardfamilydetails);
        othercard = findViewById(R.id.otherdetailscard);
        contactcard = findViewById(R.id.contactdetails);
        addresscard = findViewById(R.id.cardaddressdetails);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("View Image");

        tvName=(TextView)findViewById(R.id.tvName);
        tvAge=(TextView)findViewById(R.id.tvAge);
        tvGender=(TextView)findViewById(R.id.tvGender);
        tvDob=(TextView)findViewById(R.id.tvDob);
        tvGotra=(TextView)findViewById(R.id.tvGotra);
        tvFather=(TextView)findViewById(R.id.tvFathrer);
        tvMother=(TextView)findViewById(R.id.tvMother);
        tvFatherOccu=(TextView)findViewById(R.id.tvFatherOccu);
        tvMotherOccu=(TextView)findViewById(R.id.tvMotherOccu);
        tvEducation=(TextView)findViewById(R.id.tvEducation);
        tvOccupation=(TextView)findViewById(R.id.tvOccupation);
        tvOccupationnew=(TextView)findViewById(R.id.tvOccupation1);
        tvIncome=(TextView)findViewById(R.id.tvIncome);
        tvMobile=(TextView)findViewById(R.id.tvMobile);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        tvAddress=(TextView)findViewById(R.id.tvAddress);
        tvCity=(TextView)findViewById(R.id.tvCity);
        tvState=(TextView)findViewById(R.id.tvState);
        tvStatus=(TextView)findViewById(R.id.tvStatus);
        tvImage=(ImageView)findViewById(R.id.allImg);
        imageView=(ImageView)findViewById(R.id.image);
        tvCity1=(TextView)findViewById(R.id.tvCity1);
        tvState1=(TextView)findViewById(R.id.tvState1);


        Intent in = getIntent();
        id = in.getStringExtra("id");
        tvName.setText(in.getStringExtra("name"));
        tvAge.setText(in.getStringExtra("age"));
        tvGender.setText(in.getStringExtra("gender"));
        tvDob.setText(in.getStringExtra("dob"));
        tvGotra.setText(in.getStringExtra("gotra"));
        tvStatus.setText(in.getStringExtra("status"));
        tvFather.setText(in.getStringExtra("father"));
        tvMother.setText(in.getStringExtra("mother"));
        tvFatherOccu.setText(in.getStringExtra("fatherocc"));
        tvMotherOccu.setText(in.getStringExtra("motherocc"));
        tvEducation.setText(in.getStringExtra("education"));
        tvOccupation.setText(in.getStringExtra("occupation"));
        tvOccupationnew.setText(in.getStringExtra("occupation"));
        tvIncome.setText(in.getStringExtra("income"));
        tvMobile.setText(in.getStringExtra("mobile"));
        tvEmail.setText(in.getStringExtra("email"));
        tvAddress.setText(in.getStringExtra("address"));
        tvCity.setText(in.getStringExtra("city"));
        tvState.setText(in.getStringExtra("state"));
        tvCity1.setText(in.getStringExtra("city"));
        tvState1.setText(in.getStringExtra("state"));

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 about.setBackgroundResource(R.drawable.gradienttexttabfamily);
                 family.setBackgroundResource(R.drawable.gradienttexttab);
                familycard.setVisibility(v.GONE);
                addresscard.setVisibility(v.GONE);
                othercard.setVisibility(v.VISIBLE);
                contactcard.setVisibility(v.VISIBLE);

            }
        });

        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about.setBackgroundResource(R.drawable.gradienttexttab);
                family.setBackgroundResource(R.drawable.gradienttexttabfamily);

                othercard.setVisibility(v.GONE);
                contactcard.setVisibility(v.GONE);
                familycard.setVisibility(v.VISIBLE);
                addresscard.setVisibility(v.VISIBLE);

            }
        });


        Bundle args = new Bundle();
        args.putString("id",id);
        args.putString("name", String.valueOf(tvName));
        args.putString("gotra", String.valueOf(tvGotra));
        args.putString("gender",String.valueOf(tvGender));
        args.putString("status",String.valueOf(tvStatus));
        args.putString("age",String.valueOf(tvAge)+" "+"Years");
        args.putString("dob",String.valueOf(tvDob).substring(0,10));
        args.putString("father",String.valueOf(tvFather));
        args.putString("mother",String.valueOf(tvMother));
        args.putString("fatherocc",String.valueOf(tvFatherOccu));
        args.putString("motherocc",String.valueOf(tvMotherOccu));
        args.putString("education",String.valueOf(tvEducation));
        args.putString("occupation",String.valueOf(tvOccupation));
        args.putString("income",String.valueOf(tvIncome));
        args.putString("mobile",String.valueOf(tvMobile));
        args.putString("email",String.valueOf(tvEmail));
        args.putString("address",String.valueOf(tvAddress));
        args.putString("city",String.valueOf(tvCity));
        args.putString("state",String.valueOf(tvState));
        args.putString("profile",String.valueOf(tvImage));
        fu.setArguments(args);
        fd.setArguments(args);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
/*
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);*/

        /*tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
*/
        tvImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ViewFulProfile.this,AllImageActivity.class);
                in.putExtra("id",id);
                startActivity(in);
            }
        });

         if(in.getStringExtra("profile").equals("dummy.png")){
           imageView.setImageResource(R.drawable.man);
         }else{
          Picasso.with(ViewFulProfile.this).load(imgpath+in.getStringExtra("profile")).into(imageView);
           }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserDetailsfragment(), "About");
        adapter.addFragment(new FamilyDetailsfragment(), "Family");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }


        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
