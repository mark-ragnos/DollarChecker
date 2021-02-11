package com.example.dollarchecker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dollarchecker.testretrofit.MyBroadcastReceiver;
import com.example.dollarchecker.testretrofit.MyService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity {

    //View elements
    private Button btn_save;
    private EditText et_value;
    private ListView lv_history;

    //
    DollarData data;
    private MyBroadcastReceiver receiver = new MyBroadcastReceiver();

    SharedPreferences sPref;
    final String SAVED_VALUE = "VAL";


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test();
        btn_save = findViewById(R.id.btn_saveuservalue);
        et_value = findViewById(R.id.et_value);
        lv_history = findViewById(R.id.lv_history);

        sPref = getPreferences(MODE_PRIVATE);
        et_value.setText(sPref.getString(SAVED_VALUE, "0,00"));

        //simple save user value
        btn_save.setOnClickListener(v -> {
            SharedPreferences.Editor ed = sPref.edit();
            ed.putString(SAVED_VALUE, et_value.getText().toString());
            ed.commit();
            Toast.makeText(MainActivity.this, R.string.user_value_saved, Toast.LENGTH_LONG).show();

            this.registerReceiver(receiver, new IntentFilter("android.intent.action.TIME_TICK"));
        });

        //data
        data = new DollarData();

        //adapter
        DollarData.vals.setValueList(new ArrayList<Record>());
        DollarData.adapter = new DollarListAdapter(this,DollarData.vals.getValueList());
        lv_history.setAdapter(DollarData.adapter);

        data.getDollarHistory();
    }
}