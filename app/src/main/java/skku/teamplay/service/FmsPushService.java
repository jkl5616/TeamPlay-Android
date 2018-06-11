package skku.teamplay.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import skku.teamplay.R;
import skku.teamplay.activity.MainActivity;
import skku.teamplay.app.TeamPlayApp;
import skku.teamplay.util.SharedPreferencesUtil;

public class FmsPushService extends FirebaseMessagingService {

    private int id = 1;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String title = remoteMessage.getData().get("title");
        if(title != null && title.length() > 0) {
            String content = remoteMessage.getData().get("content");
            showNoti(title, content);
        } else {
            String penalty_description = remoteMessage.getData().get("penalty_description");
            String penalty_seconds = remoteMessage.getData().get("penalty_seconds");
            SharedPreferencesUtil.putString("penalty_description", penalty_description);
            SharedPreferencesUtil.putString("penalty_seconds", penalty_seconds);
            SharedPreferencesUtil.putInt("leftTime", 120);
            startService(new Intent(getBaseContext(), PenaltyService.class));
        }
    }

    private void showNoti(String title, String content) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "teamplay_channel";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "teamplay", NotificationManager.IMPORTANCE_MAX);
            notificationChannel.setDescription("teamplay_push");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_pac_icon)
                .setTicker("TeamPlay")
                .setContentTitle(title)
                .setContentText(content);
        notificationManager.notify(id++, notificationBuilder.build());
    }
}
