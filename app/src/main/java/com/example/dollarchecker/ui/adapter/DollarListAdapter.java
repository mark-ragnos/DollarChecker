package com.example.dollarchecker.ui.adapter;

import android.text.TextUtils;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DiffUtil;

import com.example.dollarchecker.R;
import com.example.dollarchecker.model.Record;
import com.example.dollarchecker.ui.ListItemViewModel;
import com.ravikwow.databinding.adapter.RecyclerViewAdapter;

import java.util.Objects;

public class DollarListAdapter extends RecyclerViewAdapter<Record, ListItemViewModel> {
    public DollarListAdapter(LifecycleOwner lifecycleOwner) {
        super(new DiffUtil.ItemCallback<Record>() {
            // Тут мы проверяем таже самая это сущность или нет
            @Override
            public boolean areItemsTheSame(Record record, Record t1) {
                return TextUtils.equals(record.getId(), t1.getId());
            }

            // Тут проверяем одинаковые ли объекты, если это одна сущность
            // Например, нам с сервера пришла запись, а она уже есть в списке
            @Override
            public boolean areContentsTheSame(Record record, Record t1) {
                return Objects.equals(record, t1);
            }
        }, lifecycleOwner, R.layout.list_item);
    }


    @Override
    protected void bindViewModel(Record record, ListItemViewModel listItemViewModel, int i) {
        listItemViewModel.bind(record);
    }
}
