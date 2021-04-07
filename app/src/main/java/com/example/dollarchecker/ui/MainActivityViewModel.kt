package com.example.dollarchecker.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.dollarchecker.model.Record
import com.example.dollarchecker.network.CurrencyHelper
import io.reactivex.Single
import java.util.*

class MainActivityViewModel(private val currencyHelper: CurrencyHelper) : ViewModel() {

    private lateinit var record: Single<List<Record>>
    private lateinit var recordList: List<Record>

    fun getRecords(start: Calendar, end: Calendar): Single<List<Record>> {
        record = currencyHelper.getLastList(start, end)
        return record
    }
}