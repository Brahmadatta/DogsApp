package com.example.dogsapp.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dogsapp.R;
import com.example.dogsapp.view.MainActivity;

public class NotificationHelper {

    private static final String CHANNEL_ID = "dogs channel";
    private static final int NOTIFICATION_ID = 123;

    private static NotificationHelper instance;
    private Context mContext;

    private NotificationHelper(Context context) {
        this.mContext = context;
    }

    public static NotificationHelper getInstance(Context context) {
        if (instance == null) {
            instance = new NotificationHelper(context);
        }
        return instance;
    }

    public void createNotification(){

        createNotificationChannel();
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0,intent,0);

        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dog);

        Notification notification = new NotificationCompat.Builder(mContext,CHANNEL_ID)
                .setSmallIcon(R.drawable.dog)
                .setLargeIcon(bitmap)
                .setContentTitle("Dogs Retrieved")
                .setContentText("This is a notification to let you know that dog info has been retrieved")
                .setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null))
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();


        NotificationManagerCompat.from(mContext).notify(NOTIFICATION_ID,notification);

    }

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String name = CHANNEL_ID;
            String description = "Dogs Retrieved notifications channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);
            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);

        }
    }
}
