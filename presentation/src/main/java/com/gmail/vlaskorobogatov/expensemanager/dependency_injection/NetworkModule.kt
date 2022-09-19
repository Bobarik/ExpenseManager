package com.gmail.vlaskorobogatov.expensemanager.dependency_injection

import com.gmail.vlaskorobogatov.model.api.CurrencyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideApi(): CurrencyApi {
        return CurrencyApi.create()
    }
}