package com.example.dollarchecker.ui

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.dollarchecker.model.Record


class ListItemViewModel {
    // Лучше для каждого элемента на экране сделать свою переменную, тогда можно будет поменять
    // только ее и сделать локику для изменения, когда в модели приходит значение в одном виде,
    // а тебе надо отобразить в другом.
    val date = ObservableField<String>()
    val value = ObservableField<String>()

    fun bind(record: Record) {
        date.set(record.date)
        value.set(record.value)
    }
}