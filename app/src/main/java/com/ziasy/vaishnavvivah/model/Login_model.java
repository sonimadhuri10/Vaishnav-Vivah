package com.ziasy.vaishnavvivah.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Login_model {

       @SerializedName("checksum")
        public String checksum;

        @SerializedName("otp")
        public String otp;

     /*   @SerializedName("responce")
        public String responce;*/

        @SerializedName("status")
        public String status;

        @SerializedName("responce")
        public ArrayList<userdetail> user = new ArrayList<>();

        public static class userdetail {

                @SerializedName("id")
                public String id;

                @SerializedName("otp")
                public String otp;

                @SerializedName("pri_fix")
                public String pri_fix;

                @SerializedName("user_name")
                public String user_name;

                @SerializedName("user_email")
                public String user_email;

                @SerializedName("user_mobile")
                public String user_mobile;

                @SerializedName("gotra")
                public String gotra;


                @SerializedName("marital_status")
                public String marital_status;


                @SerializedName("dob")
                public String dob;


                @SerializedName("profile_pic")
                public String profile_pic;


                @SerializedName("payment_status")
                public String payment_status;


                @SerializedName("education")
                public String education;


                @SerializedName("occupation")
                public String occupation;

                @SerializedName("gender")
                public String gender;


                @SerializedName("address")
                public String address;

                @SerializedName("city")
                public String city;

                @SerializedName("state")
                public String state;

                @SerializedName("mother_name")
                public String mother_name;

                @SerializedName("mother_occupation")
                public String mother_occupation;

                @SerializedName("father_name")
                public String father_name;

                @SerializedName("father_occupation")
                public String father_occupation;


                @SerializedName("income")
                public String income;



                @SerializedName("profile_for")
                public String profile_for;


                @SerializedName("intrest")
                public String intrest;


        }

}
