package com.example.dollarchecker.ui

import androidx.lifecycle.ViewModel
import com.example.dollarchecker.model.Record

class ListItemViewModel(private val record: Record):ViewModel() {

    fun getRecord():Record{
        return record
    }
}