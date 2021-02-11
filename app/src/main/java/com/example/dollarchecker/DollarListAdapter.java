package com.example.dollarchecker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DollarListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<Record> itemList;

    public DollarListAdapter(Context context, List<Record> itemList) {
        this.context = context;
        this.itemList = itemList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Record getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(convertView == null)
            view = inflater.inflate(R.layout.list_item, parent, false);

        Record record = getItem(position);
        ((TextView)view.findViewById(R.id.li_tv_date)).setText(record.getDate());
        ((TextView)view.findViewById(R.id.li_tv_value)).setText(record.getValue());

        return view;
    }

}
