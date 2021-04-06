package com.example.dollarchecker.di

import com.example.dollarchecker.network.CurrencyHelper
import dagger.Component
import javax.inject.Singleton


@Component(modules = [ApiModule::class])
interface DollarComponent {

    @Singleton
    fun getDollarApiHelper(): CurrencyHelper

}