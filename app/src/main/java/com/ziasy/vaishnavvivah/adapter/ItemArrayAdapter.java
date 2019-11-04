package com.ziasy.vaishnavvivah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.ziasy.vaishnavvivah.R;
import com.ziasy.vaishnavvivah.model.ListModel;

import java.util.ArrayList;


/**
 * Created by ANDROID on 21-Mar-18.
 */

public class ItemArrayAdapter extends ArrayAdapter {

    ArrayList<ListModel> al = null;
    Context context;

    public ItemArrayAdapter(Context context, ArrayList<ListModel> al) {
        super(context, R.layout.list_item, al);
        this.context=context;
        this.al = al;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = new ViewHolder();
        int pos = position;
        ListModel lm = al.get(pos);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            vh.tvTitle=(TextView)convertView.findViewById(R.id.tvTitle);
            vh.icon=(ImageView)convertView.findViewById(R.id.image);
            convertView.setTag(vh);
        } else {
            vh =(ViewHolder)convertView.getTag();
        }
        vh.tvTitle.setText(lm.getTitle());
        vh.icon.setImageResource(lm.getIcon());

        return convertView;
    }

    class ViewHolder {
        ImageView icon;
        TextView tvTitle;
    }
}