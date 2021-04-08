package com.example.dollarchecker.di.modules

import com.example.dollarchecker.network.CurrencyHelper
import dagger.Module
import dagger.Provides

@Module
class ApiModule {

    @Provides
    fun getCurrencyHelper(): CurrencyHelper {
        return CurrencyHelper.getInstance()
    }
}