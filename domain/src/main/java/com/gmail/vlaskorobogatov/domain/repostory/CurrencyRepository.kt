package com.gmail.vlaskorobogatov.domain.repostory

import com.gmail.vlaskorobogatov.domain.Currency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun getCurrencyById(name: String): Flow<Currency>

    suspend fun updateCurrencies()

    fun getCurrencies(): Flow<List<Currency>>
}