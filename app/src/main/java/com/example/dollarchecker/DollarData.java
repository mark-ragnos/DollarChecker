package com.example.dollarchecker;


import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import androidx.annotation.RequiresApi;
import com.example.dollarchecker.testretrofit.CbrApi;
import java.io.IOException;
import java.util.Collections;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class DollarData {
    public static DollarData baseDollarData;
    public static DollarListAdapter adapter;
    public final static ValCurs vals = new ValCurs();

    private Retrofit retrofit;
    private CbrApi cbrApi;
    private Disposable disposable;
    private SimpleDateFormat format;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public DollarData(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://www.cbr.ru/scripts/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        cbrApi = retrofit.create(CbrApi.class);
        format = new SimpleDateFormat("dd/MM/yyyy");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Record getToday(){
        Calendar calendar = Calendar.getInstance();

        Call<ValCurs> result = cbrApi.getDollarsByRange(format.format(calendar.getTime()), format.format(calendar.getTime()));

        Record ret = null;
        try {
            Response<ValCurs> resp = result.execute();
            if(resp.body().getValueList()  != null)
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

        Single<ValCurs> result = cbrApi.getDollarsByRangeRX(format.format(calendarStart.getTime()), format.format(calendarEdn.getTime()));

        disposable = result.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(r -> {
                    vals.getValueList().addAll(r.getValueList());
                    Collections.reverse(vals.getValueList());
                    adapter.notifyDataSetChanged();
                });
    }

    public void clearRx(){
        disposable.dispose();
    }
}
