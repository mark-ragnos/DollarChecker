package com.example.dollarchecker;


import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dollarchecker.list.DollarListAdapter;
import com.example.dollarchecker.list.ListController;
import com.example.dollarchecker.network.CurrencyHelper;
import com.example.dollarchecker.network.Record;
import com.example.dollarchecker.notification.DollarBroadcattReciver;
import com.example.dollarchecker.utilities.CalendarManipulation;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    //Init elements
    private Button btn_save;
    private EditText et_value;
    private ListView lv_history;

    private SharedPreferences preferences;
    public static final String USER_VALUE = "user_value";
    //
    AlarmManager alarmManager;
    ListController listController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_save = findViewById(R.id.btn_saveuservalue);
        et_value = findViewById(R.id.et_value);
        lv_history = findViewById(R.id.lv_history);

        //Alarm
        startAlarm();

        preferences = getSharedPreferences(USER_VALUE, Context.MODE_PRIVATE);
        et_value.setText(preferences.getString(USER_VALUE, "0.00"));
        //simple save user value
        btn_save.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USER_VALUE, et_value.getText().toString()).apply();
            Toast.makeText(MainActivity.this, R.string.user_value_saved, Toast.LENGTH_LONG).show();
        });

        //calendar dates
        Calendar calendarEnd = Calendar.getInstance();
        Calendar calendarStart = CalendarManipulation.getStart(calendarEnd);

        //list manipulations
        CurrencyHelper currencyHelper = new CurrencyHelper();
        listController = new ListController(this, lv_history);
        listController.fillList(currencyHelper.getLastList(calendarStart, calendarEnd));

    }


    private void startAlarm() {
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DollarBroadcattReciver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis() + 1000, 10000, pendingIntent);
    }

    @Override
    protected void onDestroy() {
        listController.clearDisposable();
        super.onDestroy();
    }
}