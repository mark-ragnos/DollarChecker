package com.example.dollarchecker.ui;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.dollarchecker.R;
import com.example.dollarchecker.databinding.ActivityMainBinding;
import com.example.dollarchecker.di.AppViewModelFactory;
import com.example.dollarchecker.model.Record;
import com.example.dollarchecker.notification.DollarBroadcattReciver;
import com.example.dollarchecker.ui.adapter.DollarListAdapter;
import com.example.dollarchecker.ui.adapter.DollarPageAdapter;
import com.example.dollarchecker.utility.CalendarManipulation;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    public static final String USER_VALUE = "user_value";
    AlarmManager alarmManager;
    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private DollarListAdapter adapter;
    private DollarPageAdapter pageAdapter;
    private SharedPreferences preferences;
    private CompositeDisposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(this.getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProviders.of(this, new AppViewModelFactory()).get(MainActivityViewModel.class);
        binding.setViewModel(viewModel);
        disposable = new CompositeDisposable();
        init();
        startAlarm();
        //oldList();


        binding.refresh.setOnRefreshListener(() -> {
            pageAdapter.reload();
            binding.refresh.setRefreshing(false);
        });
    }

    private void init() {
        // Не надо все время создавать адаптер. Нужно его создать один раз
        // и потом в него передавать данные, а он сам ищет разницу
        pageAdapter = DollarPageAdapter.Companion.create();
        binding.lvHistory.setAdapter(pageAdapter);
        binding.lvHistory.setHasFixedSize(true);

        preferences = getSharedPreferences(USER_VALUE, Context.MODE_PRIVATE);
        binding.etValue.setText(preferences.getString(USER_VALUE, "0.00"));

        binding.btnSaveuservalue.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(USER_VALUE, binding.etValue.getText().toString()).apply();
            Toast.makeText(MainActivity.this, R.string.user_value_saved, Toast.LENGTH_LONG).show();
        });
    }

    private void oldList(){
        adapter = new DollarListAdapter(this);
        binding.lvHistory.setAdapter(adapter);

        Calendar calendarEnd = Calendar.getInstance();
        Calendar calendarStart = CalendarManipulation.getStart(calendarEnd);

        disposable.add(setupList(viewModel.getRecords(calendarStart, calendarEnd))
                .subscribe(res -> adapter.setItems(res)));

        binding.refresh.setOnRefreshListener(() -> {
            Calendar calendarEnd2 = CalendarManipulation.getStart(Calendar.getInstance());
            Calendar calendarStart2 = CalendarManipulation.getStart2(calendarEnd);

            disposable.clear();
            disposable.add(setupList(viewModel.getRecords(calendarStart2, calendarEnd2))
                    .subscribe(res -> {
                                adapter.setItems(res);
                                binding.refresh.setRefreshing(false);
                            }
                    )
            );
        });
    }

    private Single<List<Record>> setupList(Single<List<Record>> list) {
        return list
                .subscribeOn(Schedulers.io())
                .map(r -> {
                    Collections.reverse(r);
                    return r;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    private void startAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
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
        super.onDestroy();
        disposable.clear();
    }
}