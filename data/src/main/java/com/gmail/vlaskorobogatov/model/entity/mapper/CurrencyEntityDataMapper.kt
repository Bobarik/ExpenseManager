package com.gmail.vlaskorobogatov.model.entity.mapper

import com.gmail.vlaskorobogatov.domain.Currency
import com.gmail.vlaskorobogatov.model.entity.CurrencyEntity

fun toCurrency(currencyEntity: CurrencyEntity): Currency {
    return Currency(currencyEntity.currencyId, currencyEntity.rateToDollar)
}

fun toCurrencyEntity(currency: Currency): CurrencyEntity {
    return CurrencyEntity(currency.currencyId, currency.rateToDollar)
}

fun toCurrencyList(currencyCollection: Collection<CurrencyEntity>): List<Currency> {
    val currencyList = ArrayList<Currency>()
    for(i in currencyCollection) {
        currencyList.add(toCurrency(i))
    }
    return currencyList
}

fun toCurrencyEntityList(currencyCollection: Collection<Currency>): List<CurrencyEntity> {
    val currencyEntityList = ArrayList<CurrencyEntity>()
    for(i in currencyCollection) {
        currencyEntityList.add(toCurrencyEntity(i))
    }
    return currencyEntityList
}
