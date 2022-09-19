package com.gmail.vlaskorobogatov.model.repository

import android.util.Log
import com.gmail.vlaskorobogatov.domain.Currency
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import com.gmail.vlaskorobogatov.model.api.CurrencyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyNetworkRepository @Inject constructor(private val service: CurrencyApi, private val currencyRepository: CurrencyRoomRepositoryImpl): CurrencyRepository {

    override suspend fun updateCurrencies() {
        val response = service.getLatest("f9e7ce5f49324131a424885bc32756a0", false)
        withContext(Dispatchers.IO) {
            if (response.isSuccessful) {
                val items = response.body()
                if (items != null) {
                    val currencyList = ArrayList<Currency>()
                    for (i in items.rates) {
                        currencyList.add(Currency(i.key, i.value))
                    }
                    currencyRepository.insert(currencyList)
                } else {
                    Log.e("RETROFIT_ERROR", "Rates list is empty")
                }
            } else {
                Log.e("RETROFIT_ERROR", response.code().toString())
            }
        }
    }

    override fun getCurrencies(): Flow<List<Currency>> {
        return currencyRepository.getCurrencies()
    }

    override fun getCurrencyById(name: String): Flow<Currency> {
        return currencyRepository.getCurrencyById(name)
    }
}