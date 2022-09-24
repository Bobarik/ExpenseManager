package com.gmail.vlaskorobogatov.domain.interactor.account

import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAccountCurrencyUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<String, Flow<String>>() {

    override fun execute(parameters: String): Flow<String> {
        return accountRepository.getAccount(parameters).map { x -> x.currencyId }
    }
}