package com.gmail.vlaskorobogatov.domain.interactor.account

import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.domain.interactor.SuspendUseCase
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) :
    SuspendUseCase<Account, Unit>() {

    override suspend fun execute(parameters: Account) {
        return accountRepository.updateAccount(listOf(parameters))
    }
}