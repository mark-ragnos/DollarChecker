package com.example.dollarchecker.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dollarchecker.BR;
import com.example.dollarchecker.R;
import com.example.dollarchecker.databinding.ListItemBinding;
import com.example.dollarchecker.model.Record;
import com.example.dollarchecker.ui.ListItemViewModel;
import com.example.dollarchecker.ui.MainActivityViewModel;
import com.ravikwow.databinding.adapter.RVAdapterViewHolder;
import com.ravikwow.databinding.adapter.RecyclerViewAdapter;

import java.util.List;

public class DollarListAdapter extends RecyclerViewAdapter<Record, ListItemViewModel> {

    public DollarListAdapter() {
        super(new CompareCallbacks<Record>() {
            @Override
            public boolean areItemsTheSame(Record record, Record t1) {
                return record.getDate() == record.getDate();
            }

            @Override
            public boolean areContentsTheSame(Record record, Record t1) {
                return record.getDate() == record.getDate();
            }
        }, R.layout.list_item);
    }


    @Override
    protected void bindViewModel(Record record, ListItemViewModel listItemViewModel, int i) {
        listItemViewModel.bind(record);
    }

    @Override
    public void onBindViewHolder(RVAdapterViewHolder<ListItemViewModel> holder, int position, List<Object> payloads) {
        if (payloads == null || payloads.isEmpty())
            super.onBindViewHolder(holder, position, payloads);
        else {
            Record record = (Record) payloads.get(payloads.size() - 1);
            holder.getVm().bind(record);
        }
    }
}
