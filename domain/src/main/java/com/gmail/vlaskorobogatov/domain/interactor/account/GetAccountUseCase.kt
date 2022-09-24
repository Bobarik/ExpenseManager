package com.gmail.vlaskorobogatov.domain.interactor.account

import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<String, Flow<Account>>() {

    override fun execute(parameters: String): Flow<Account> {
        return accountRepository.getAccount(parameters)
    }
}