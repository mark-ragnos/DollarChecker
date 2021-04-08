package com.example.dollarchecker.network;

import android.util.Log;

import com.example.dollarchecker.model.Record;
import com.example.dollarchecker.model.ValCurs;
import com.example.dollarchecker.utility.DataConverter;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class CurrencyHelper {
    Retrofit retrofit;

    public CurrencyHelper() {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.cbr.ru/scripts/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Single<List<Record>> getLastList(Calendar date1, Calendar date2) {
        Single<ValCurs> dollarsByRangeRX = retrofit.create(CbrApi.class).getDollarsByRangeRX(DataConverter.getDateString(date1), DataConverter.getDateString(date2));
        return dollarsByRangeRX.flatMap(r -> Single.just(r.getValueList()));
    }

    public Single<Record> getToday(Calendar date) {
        Single<ValCurs> dollarsByRangeRX = retrofit.create(CbrApi.class).getDollarsByRangeRX(DataConverter.getDateString(date), DataConverter.getDateString(date));
        Single<Record> recordSingle = dollarsByRangeRX.flatMap(r -> {
            if (r.getValueList().size() > 0)
                return Single.just(r.getValueList().get(0));
            return null;
        });
        return recordSingle;
    }

    private static CurrencyHelper helper;

    public static CurrencyHelper getInstance() {
        if (helper == null)
            helper = new CurrencyHelper();
        return helper;
    }
}
