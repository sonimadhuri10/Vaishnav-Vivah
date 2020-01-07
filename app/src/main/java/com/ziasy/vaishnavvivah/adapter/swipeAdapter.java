package com.ziasy.vaishnavvivah.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.ziasy.vaishnavvivah.NetworkManager.APIClient;
import com.ziasy.vaishnavvivah.NetworkManager.APIInterface;
import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.activity.swipeActivity;
import com.ziasy.vaishnavvivah.comman.ConnectionDetector;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.ziasy.vaishnavvivah.model.ImageModel;

import java.util.ArrayList;

public class swipeAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    ConnectionDetector cd ;
    ProgressDialog pd ;
    APIInterface apiInterface ;
    private ArrayList<ImageModel.userdetail> imageModelList;
    int possss=0 ;
    String baseurl="http://joietouch.com/matrimoney/images/";
    SessionManagment sd;
    private com.ziasy.vaishnavvivah.activity.swipeActivity swipeActivity;


    public swipeAdapter(Context context, ArrayList<ImageModel.userdetail> feedItemList) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageModelList = feedItemList;
        this.baseurl= baseurl;
        apiInterface = APIClient.getClient().create(APIInterface.class);
        cd = new ConnectionDetector(context);
        pd = new ProgressDialog(context);
        pd.setMessage("Please wait...");
        pd.setCancelable(false);
        sd = new SessionManagment(context);
        this.swipeActivity = (swipeActivity) context;
    }


    @Override
    public int getCount() {
        return (null != imageModelList ? imageModelList.size() : 0);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }



    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.viewimage_layout, container, false);

        final ImageModel.userdetail get = imageModelList.get(position);

         ImageView photoView = (ImageView) itemView.findViewById(R.id.image);
      //  TextView tvDelet = (TextView)itemView.findViewById(R.id.tvDelet);

        Picasso.with(mContext).load(baseurl+get.img_name).error(R.drawable.man).into(photoView);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((LinearLayout) object).removeView(container);
    }
}
