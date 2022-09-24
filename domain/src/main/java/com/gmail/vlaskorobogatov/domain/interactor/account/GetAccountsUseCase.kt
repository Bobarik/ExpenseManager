package com.gmail.vlaskorobogatov.domain.interactor.account

import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) : UseCase<Unit, Flow<List<Account>>>() {

    override fun execute(parameters: Unit): Flow<List<Account>> {
        return accountRepository.getAccounts()
    }
}