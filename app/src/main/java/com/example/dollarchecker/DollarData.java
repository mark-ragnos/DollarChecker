package com.example.dollarchecker;


import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.dollarchecker.testretrofit.CbrApi;

import java.io.IOException;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class DollarData {
    public static DollarData baseDollarData;
    public static DollarListAdapter adapter;
    public final static ValCurs vals = new ValCurs();

    private Retrofit retrofit;
    private CbrApi cbrApi;


    public DollarData(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.cbr.ru/scripts/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        cbrApi = retrofit.create(CbrApi.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Record getToday(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");


        Call<ValCurs> result = cbrApi.getDollarsByRange(format.format(calendar.getTime()), format.format(calendar.getTime()));

        Record ret = null;
        try {
            Response<ValCurs> resp = result.execute();
            ret = resp.body().getValueList().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ret;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getDollarHistory(){
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEdn = Calendar.getInstance();
        calendarStart.add(Calendar.MONTH, -1);

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Call<ValCurs> result = cbrApi.getDollarsByRange(format.format(calendarStart.getTime()), format.format(calendarEdn.getTime()));

        result.enqueue(new Callback<ValCurs>() {
            @Override
            public void onResponse(Call<ValCurs> call, Response<ValCurs> response) {
                if(!response.isSuccessful()){
                    Log.d("TAG", "Code: " + response.code());
                    return;
                }
                ValCurs res = response.body();

                vals.getValueList().addAll(res.getValueList());
                Collections.reverse(vals.getValueList());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ValCurs> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }
}
