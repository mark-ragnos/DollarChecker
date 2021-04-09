package com.example.dollarchecker.ui.adapter


import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import com.example.dollarchecker.R
import com.example.dollarchecker.model.Record
import com.example.dollarchecker.network.CbrApiNew
import com.example.dollarchecker.ui.ListItemViewModel
import com.example.dollarchecker.utility.changeMonth
import com.example.dollarchecker.utility.getAsText
import com.ravikwow.databinding.adapter.RecyclerViewPagedAdapter
import java.util.*

class DollarPageAdapter(
        diffCallback: DiffUtil.ItemCallback<Record>,
        private val getData: GetData<Record>,
        pageSize: Int,
        lifecycleOwner: LifecycleOwner,
        vararg resIdsViews: Int
) : RecyclerViewPagedAdapter<Record, ListItemViewModel>(diffCallback, getData, pageSize, lifecycleOwner, *resIdsViews) {

    override fun bindViewModel(model: Record?, viewModel: ListItemViewModel?, position: Int) {
        model?.let {
            viewModel?.bind(model)
        }
    }

    class GetDataRecord : GetData<Record> {

        var todayOrLast: Calendar = Calendar.getInstance()

        override fun getData(start: Long, pages: Int): List<Record>? {
            Log.d("TEST", "start = $start || pages = $pages")
            if (start == 0L)
                todayOrLast = Calendar.getInstance()
            else
                todayOrLast.add(Calendar.HOUR, -24)
            val start = todayOrLast.clone() as Calendar
            start.changeMonth(-pages)
            val result = CbrApiNew.create().getMouthData(start.getAsText(), todayOrLast.getAsText()).execute().body()?.valueList
            result?.reverse()
            todayOrLast = start
            return result
        }
    }


    companion object {
        private const val pageSize = 2

        fun create(lifecycleOwner: LifecycleOwner): DollarPageAdapter {
            return DollarPageAdapter(diffCallback = object : DiffUtil.ItemCallback<Record>() {
                override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
                    return oldItem == newItem
                }

            },
                    getData = GetDataRecord(),
                    pageSize = pageSize,
                    lifecycleOwner = lifecycleOwner,
                    R.layout.list_item)
        }
    }
}
