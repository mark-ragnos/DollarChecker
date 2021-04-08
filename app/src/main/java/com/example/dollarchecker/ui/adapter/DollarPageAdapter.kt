package com.example.dollarchecker.ui.adapter


import androidx.recyclerview.widget.DiffUtil
import com.example.dollarchecker.R
import com.example.dollarchecker.model.Record
import com.example.dollarchecker.network.CbrApiNew
import com.example.dollarchecker.ui.ListItemViewModel
import com.example.dollarchecker.utility.changeMonth
import com.example.dollarchecker.utility.getAsText
import com.ravikwow.databinding.adapter.RecyclerViewPagedAdapter
import kotlinx.coroutines.*
import java.util.*

class DollarPageAdapter(
        diffCallback: DiffUtil.ItemCallback<Record>,
        getData: GetData<Record>,
        pageSize: Int,
        vararg resIdsViews: Int
) : RecyclerViewPagedAdapter<Record, ListItemViewModel>(diffCallback, getData, pageSize, *resIdsViews) {

    override fun bindViewModel(model: Record?, viewModel: ListItemViewModel?, position: Int) {
        model?.let {
            viewModel?.bind(model)
        }
    }

    class GetDataRecord : GetData<Record> {

        var todayOrLast: Calendar = Calendar.getInstance()

        override fun getData(start: Long, pages: Int): List<Record>? {
            val start = todayOrLast.clone() as Calendar
            start.changeMonth(-pages)
            val result = CbrApiNew.create().getMouthData(start.getAsText(), todayOrLast.getAsText()).execute().body()?.valueList
            result?.reverse()
            todayOrLast = start
            return result
        }
    }

    companion object {
        val pageSize = 2

        fun create(): DollarPageAdapter {
            return DollarPageAdapter(diffCallback = object : DiffUtil.ItemCallback<Record>() {
                override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
                    return oldItem.date == newItem.date
                }

                override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
                    return oldItem == newItem
                }

            },
                    getData = GetDataRecord(),
                    pageSize = pageSize,
                    R.layout.list_item)
        }
    }
}
