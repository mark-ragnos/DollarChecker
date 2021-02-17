package com.example.dollarchecker.notification;

import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.dollarchecker.MainActivity;
import com.example.dollarchecker.R;
import com.example.dollarchecker.network.CurrencyHelper;
import com.example.dollarchecker.network.Record;
import com.example.dollarchecker.utilities.DataConverter;

import java.util.Calendar;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class MyService extends IntentService {

    private static final String CHANNEL_ID = "SMTSDA";
    Disposable disposable;

    public MyService() {
        super("message");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        /*DollarData feedback;
        if(DollarData.baseDollarData==null) {
            feedback = new DollarData();
        }
        else feedback = DollarData.baseDollarData;

        float userValue = Float.parseFloat(getSharedPreferences(MainActivity.USER_VALUE, MODE_PRIVATE).getString(MainActivity.USER_VALUE, "0.00"));
        Record message = feedback.getToday();

        if(message==null)
            return;

        float todayValue = Float.parseFloat(message.getValue().replace(',', '.'));

        if(todayValue < userValue)
            return;

        float diff =  todayValue - userValue;
        */
        CurrencyHelper helper = new CurrencyHelper();
        float userValue = Float.parseFloat(getSharedPreferences(MainActivity.USER_VALUE, MODE_PRIVATE).getString(MainActivity.USER_VALUE, "0.00"));
        Single<Record> today = helper.getToday(Calendar.getInstance());
        disposable = today.subscribe(r ->
        {
            if(r==null) return;
            float todayVal = DataConverter.getFloat(r.getValue());
            if(todayVal > userValue){
                makeNotification(todayVal - userValue);
            }
        });

    }

    private void makeNotification(float diff){
        NotificationManager notificationManager = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager = getSystemService(NotificationManager.class);
            CharSequence name = "channel_name";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.notify_title))
                .setContentText(getString(R.string.prifit) + diff)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(123, builder.build());
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}
