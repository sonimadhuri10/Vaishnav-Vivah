package com.ziasy.vaishnavvivah.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageModel {

    @SerializedName("status")
    public String status;

    @SerializedName("responce")
    public ArrayList<userdetail> user = new ArrayList<>();

    public static class userdetail {

        @SerializedName("id")
        public String id;

        @SerializedName("img_name")
        public String img_name;


         @SerializedName("item_id")
        public String item_id;



    }

}
