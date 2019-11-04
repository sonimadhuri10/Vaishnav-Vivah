package com.ziasy.vaishnavvivah.comman;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManagment {
    // LogCat tag
    private static String TAG = SessionManagment.class.getSimpleName();
    SharedPreferences pref;
    public String mobile;
    Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "vaishnav Vivah";
    private static final String KEY_ID = "id";
    private static final String LOGIN_STATUS = "false";
    private static final String LOGOUT_STATUS = "false";
    private static final String KEY_FCM = "fcmid";
    private static final String KEY_IMAGEPATH = "image";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_PROFILE = "false";
    private static final String KEY_PAYMENT = "false";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_MOBILE= "mob";


    public SessionManagment(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setKEY_ID(String s) {
        editor.putString("KEY_ID", s);
        editor.commit();
    }

    public String getKEY_ID() {
        return pref.getString("KEY_ID", KEY_ID);
    }


    public String getPROFILE_STATUS() {
        return pref.getString("PROFILE_STATUS", KEY_PROFILE);
    }

    public void setPROFILE_STATUS(String s) {
        editor.putString("PROFILE_STATUS", s);
        editor.commit();
    }

    public void setLOGIN_STATUS(String s) {
        editor.putString("LOGIN_STATUS", s);
        editor.commit();
    }

    public String getLOGIN_STATUS() {
        return pref.getString("LOGIN_STATUS", LOGIN_STATUS);
    }


    public void setLOGOUT_STATUS(String s) {
        editor.putString("LOGOUT_STATUS", s);
        editor.commit();
    }

    public String getLOGOUT_STATUS() {
        return pref.getString("LOGOUT_STATUS", LOGOUT_STATUS);
    }



    public String getpAYMENT_STATUS() {
        return pref.getString("PAYMENT_STATUS", KEY_PAYMENT);
    }


    public void setPAYMENT_STATUS(String s) {
        editor.putString("PAYMENT_STATUS", s);
        editor.commit();
    }



    public void setFCM(String fcm) {
        editor.putString("KEY_FCM", fcm);
        editor.commit();
    }


    public String getFCM() {
        return pref.getString("KEY_FCM", KEY_FCM);
    }



    public void setGENDER(String gen) {
        editor.putString("GENDER", gen);
        editor.commit();
    }


    public String getGENDER() {
        return pref.getString("GENDER", KEY_GENDER);
    }

    public void setIMAGEPATH(String img) {
        editor.putString("IMAGEPATH", img);
        editor.commit();
    }


    public String getIMAGEPATH() {
        return pref.getString("IMAGEPATH", KEY_IMAGEPATH);
    }



    public void setMOBILE(String mob) {
        editor.putString("MOBILE", mob);
        editor.commit();
    }


    public String getMOBILE() {
        return pref.getString("MOBILE", KEY_MOBILE);
    }



    public void setEMAIL(String email) {
        editor.putString("EMAIL", email);
        editor.commit();
    }


    public String getEMAIL() {
        return pref.getString("EMAIL", KEY_EMAIL);
    }
}
