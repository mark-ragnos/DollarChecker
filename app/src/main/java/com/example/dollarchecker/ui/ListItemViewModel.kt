package com.example.dollarchecker.ui

import androidx.lifecycle.ViewModel
import com.example.dollarchecker.model.Record

class ListItemViewModel:ViewModel {
    private lateinit var record: Record

    constructor(record: Record){
        this.record = record
    }
    constructor(){}

    fun getRecord():Record{
        return record
    }

    fun bind(record: Record){
        this.record = record
    }
}