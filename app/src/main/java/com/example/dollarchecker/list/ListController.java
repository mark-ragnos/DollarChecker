package com.example.dollarchecker.list;

import android.content.Context;
import android.util.Log;
import android.widget.ListView;

import com.example.dollarchecker.network.Record;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.DisposableContainer;
import io.reactivex.schedulers.Schedulers;

public class ListController {
    DollarListAdapter adapter;
    List<Record> list;
    Context context;
    ListView lv;
    Disposable dis;

    public ListController(Context context, ListView listView) {
        this.context = context;
        lv = listView;
    }

    public void fillList(Single<List<Record>> list){
        dis = list
                .subscribeOn(Schedulers.io())
                .map(r -> {
                    Collections.reverse(r);
                    return r;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(res -> {
                    this.list = res;
                    adapter = new DollarListAdapter(context, this.list);
                    lv.setAdapter(adapter);
                });
    }

    public void clearDisposable(){
        dis.dispose();
    }

}
