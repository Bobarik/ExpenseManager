package com.gmail.vlaskorobogatov.domain.interactor.currency

import com.gmail.vlaskorobogatov.domain.interactor.SuspendUseCase
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import javax.inject.Inject

class UpdateCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : SuspendUseCase<Unit, Unit>() {

    override suspend fun execute(parameters: Unit) {
        return currencyRepository.updateCurrencies()
    }
}