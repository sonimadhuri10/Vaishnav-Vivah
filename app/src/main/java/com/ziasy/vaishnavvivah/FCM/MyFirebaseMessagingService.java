package com.ziasy.vaishnavvivah.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.ziasy.vaishnavvivah.MainActivity;
import com.ziasy.vaishnavvivah.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ziasy.vaishnavvivah.comman.SessionManagment;


/**
 * Created by Belal on 5/27/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";
    private Intent intent;
    SessionManagment sd ;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String s = remoteMessage.getData().toString();
        sd = new SessionManagment(getApplicationContext());
        Log.d("notificationDAATA", "From: " + s);
        Log.d("notification", "From: " + remoteMessage.getData().get("message"));

       if(sd.getLOGOUT_STATUS().equals("false")){
           sendNotification(remoteMessage.getData().get("message"));//message is Parameter
       }

    }

    private void sendNotification(String messageBody) {
        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        intent.putExtra("fragment", "tips");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon2)
                .setContentTitle("Vaishnav Vivah")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}