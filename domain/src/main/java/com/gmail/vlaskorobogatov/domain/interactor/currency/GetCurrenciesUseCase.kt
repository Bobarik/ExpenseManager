package com.gmail.vlaskorobogatov.domain.interactor.currency

import com.gmail.vlaskorobogatov.domain.Currency
import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : UseCase<Unit, Flow<List<Currency>>>() {

    override fun execute(parameters: Unit): Flow<List<Currency>> {
        return currencyRepository.getCurrencies()
    }
}