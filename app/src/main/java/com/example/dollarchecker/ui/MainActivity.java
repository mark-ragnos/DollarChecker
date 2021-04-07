package com.example.dollarchecker.ui;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dollarchecker.R;
import com.example.dollarchecker.databinding.ActivityMainBinding;
import com.example.dollarchecker.di.AppViewModelFactory;
import com.example.dollarchecker.model.Record;
import com.example.dollarchecker.network.CurrencyHelper;
import com.example.dollarchecker.notification.DollarBroadcattReciver;
import com.example.dollarchecker.ui.adapter.DollarListAdapter;
import com.example.dollarchecker.ui.adapter.DollarListAdapterNew;
import com.example.dollarchecker.utility.CalendarManipulation;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.single.SingleDoOnSuccess;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private DollarListAdapter adapter;

    private SharedPreferences preferences;
    public static final String USER_VALUE = "user_value";
    //
    AlarmManager alarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProviders.of(this, new AppViewModelFactory()).get(MainActivityViewModel.class);

        init();
        startAlarm();

        Calendar calendarEnd = Calendar.getInstance();
        Calendar calendarStart = CalendarManipulation.getStart(calendarEnd);
        setupList(viewModel.getRecords(calendarStart, calendarEnd));

    }

    private void init(){
        binding.lvHistory.setLayoutManager(new LinearLayoutManager(this));
        preferences = getSharedPreferences(USER_VALUE, Context.MODE_PRIVATE);
        binding.etValue.setText(preferences.getString(USER_VALUE, "0.00"));

        binding.btnSaveuservalue.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USER_VALUE, binding.etValue.getText().toString()).apply();
            Toast.makeText(MainActivity.this, R.string.user_value_saved, Toast.LENGTH_LONG).show();
        });
    }


    private void setupList(Single<List<Record>> list){
        Disposable dis = list
                .subscribeOn(Schedulers.io())
                .map(r -> {
                    Collections.reverse(r);
                    return r;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    adapter = new DollarListAdapter(viewModel);
                    adapter.setItems(res);
                    binding.lvHistory.setAdapter(adapter);

                    //adapter = new DollarListAdapter(viewModel);
                    //adapter.setItems(res);
                    //binding.lvHistory.setAdapter(adapter);
        });
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
}