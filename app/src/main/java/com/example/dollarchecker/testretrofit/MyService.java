package com.example.dollarchecker.testretrofit;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.dollarchecker.DollarData;
import com.example.dollarchecker.R;
import com.example.dollarchecker.Record;

public class MyService extends IntentService {

    private static final String CHANNEL_ID = "SMTSDA";

    public MyService() {
        super("message");
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("TAG", "SERVICE WAS ");

        DollarData feedback;
        if(DollarData.baseDollarData==null)
            feedback = new DollarData();
        else feedback = DollarData.baseDollarData;

        float userValue = 123;

        Record message = feedback.getToday();
        if(message==null)
            return;

        float todayValue = Float.valueOf(message.getValue().replace(',', '.'));

        if(todayValue > userValue)
            return;

        float diff = userValue - todayValue;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel_name";
            String description = "getString(R.string.channel_description);";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setContentTitle(getString(R.string.notify_title))
                    .setContentText(getString(R.string.prifit) + diff)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            notificationManager.notify(123, builder.build());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("TAG", "Service destroyed");
    }
}
