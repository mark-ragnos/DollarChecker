package com.example.dollarchecker.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dollarchecker.network.CurrencyHelper
import com.example.dollarchecker.ui.MainActivityViewModel

class AppViewModelFactory:ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java))
            return (MainActivityViewModel(DaggerDollarComponent.create().getDollarApiHelper()) as T)

        throw IllegalArgumentException("Incorrect ViewModel class")
    }
}