package com.example.dollarchecker.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.dollarchecker.databinding.ListItemBinding
import com.example.dollarchecker.model.Record
import com.example.dollarchecker.ui.ListItemViewModel


class DollarListAdapterNew(private val items: List<Record>) : RecyclerView.Adapter<DollarListAdapterNew.ViewHolder>() {
    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(record: Record) {
            binding.liTvValue.text = record.value
            binding.liTvDate.text = record.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder.binding.viewModel == null){
            holder.binding.viewModel = ListItemViewModel(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
