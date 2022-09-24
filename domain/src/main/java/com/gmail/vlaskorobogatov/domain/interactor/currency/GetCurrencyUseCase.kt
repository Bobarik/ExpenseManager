package com.gmail.vlaskorobogatov.domain.interactor.currency

import com.gmail.vlaskorobogatov.domain.Currency
import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
) : UseCase<String, Flow<Currency>>() {

    override fun execute(parameters: String): Flow<Currency> {
        return currencyRepository.getCurrencyById(parameters)
    }
}