package com.ziasy.vaishnavvivah.FCM;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.ziasy.vaishnavvivah.comman.SessionManagment;
import com.google.firebase.iid.FirebaseInstanceId;


/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    SessionManagment sd;
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        sd = new SessionManagment(getApplicationContext());
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sd.setFCM(refreshedToken);
        Log.e("fcm",sd.getFCM());

    }
}
