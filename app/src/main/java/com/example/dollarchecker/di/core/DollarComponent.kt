package com.example.dollarchecker.di.core

import com.example.dollarchecker.di.modules.ApiModule
import com.example.dollarchecker.network.CurrencyHelper
import com.example.dollarchecker.ui.adapter.DollarPageAdapter
import dagger.Component
import javax.inject.Singleton


@Component(modules = [ApiModule::class])
interface DollarComponent {

    @Singleton
    fun getDollarApiHelper(): CurrencyHelper

}