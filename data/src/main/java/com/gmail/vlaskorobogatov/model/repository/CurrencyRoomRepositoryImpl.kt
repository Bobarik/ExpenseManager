package com.gmail.vlaskorobogatov.model.repository

import com.gmail.vlaskorobogatov.domain.Currency
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import com.gmail.vlaskorobogatov.model.dao.CurrencyDao
import com.gmail.vlaskorobogatov.model.entity.mapper.toCurrency
import com.gmail.vlaskorobogatov.model.entity.mapper.toCurrencyEntityList
import com.gmail.vlaskorobogatov.model.entity.mapper.toCurrencyList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRoomRepositoryImpl @Inject constructor(private val currencyDao: CurrencyDao) {
    fun getCurrencies(): Flow<List<Currency>> {
        return currencyDao.getCurrencies().map { x -> toCurrencyList(x) }
    }

    fun getCurrencyById(name: String): Flow<Currency> {
        return currencyDao.getCurrencyById(name).map { x -> toCurrency(x) }
    }

    fun insert(currency: List<Currency>) = currencyDao.insert(toCurrencyEntityList(currency))

    fun update(currency: List<Currency>) = currencyDao.update(toCurrencyEntityList(currency))

}