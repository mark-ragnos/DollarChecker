package com.example.dollarchecker.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.example.dollarchecker.R;
import com.example.dollarchecker.databinding.ListItemBinding;
import com.example.dollarchecker.model.Record;

import java.util.List;

public class DollarListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    List<Record> itemList;

    public DollarListAdapter(Context context, List<Record> itemList) {
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
        DollarViewHolder holder;

        if(convertView==null){
            ListItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.list_item, parent, false);

            holder = new DollarViewHolder(itemBinding);
            holder.view = itemBinding.getRoot();
            holder.view.setTag(holder);
        }else {
            holder = (DollarViewHolder) convertView.getTag();
        }
        holder.binding.setRecord(itemList.get(position));
        return holder.view;
    }


    private static class DollarViewHolder{
        private View view;
        private ListItemBinding binding;

        public DollarViewHolder(ListItemBinding binding){
            this.binding = binding;
            this.view = binding.getRoot();
        }
    }
}
