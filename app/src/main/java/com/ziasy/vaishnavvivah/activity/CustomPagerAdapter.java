package com.ziasy.vaishnavvivah.activity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.model.ImageModel;

import java.util.ArrayList;

public class CustomPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<ImageModel.userdetail> al = null;
    public LayoutInflater mLayoutInflater;
    View imageView;


    public CustomPagerAdapter(ViewImageActivity context, ArrayList<ImageModel.userdetail> user) {
        this.context = context;
        this.al = user;

    }

    @Override
    public int getCount() {
        return (null != al ? al.size() : 0);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override

    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = (ImageView) mLayoutInflater.inflate(R.layout.viewimage_layout, container, false);
     //   TouchImageView photoView = (TouchImageView) itemView.findViewById(R.id.photo_view);
        ImageView imageView1 = (ImageView) imageView.findViewById(R.id.image);
        // ImageView imageView = new ImageView(context);
        // int padding = context.getResources().getDimensionPixelSize(R.dimen.padding_medium);
        // imageView.setPadding(padding, padding, padding, padding);
        // imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        //imageView.setImageResource(GalImages[position]);

        Picasso.with(context).load(String.valueOf(al.get(position))).into(imageView1);
        ((ViewPager) container).addView((imageView),position);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}