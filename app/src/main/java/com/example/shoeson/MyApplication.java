package com.example.shoeson;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyApplication extends Application {
    public static final String CHANNEL_ID="chanel_id";
    @Override
    public void onCreate() {
        super.onCreate();
        createNotification(this,"title","content");
    }

    @SuppressLint("MissingPermission")
    public void createNotification(Context context, String title, String message) {
        // Khởi tạo NotificationCompat.Builder
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
//                .setSmallIcon(R.drawable.baseline_email_24)
//                .setContentTitle(title)
//                .setContentText(message)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Kiểm tra phiên bản Android
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Tạo channel cho notification trên Android 8.0 trở lên
            String channelId = "channel_id";
            String channelName = "Channel Name";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            //builder.setChannelId(channelId);
        }

        // Hiển thị notification
//        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
//        notificationManagerCompat.notify(777, builder.build());
    }
}
