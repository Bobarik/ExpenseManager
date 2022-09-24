package com.gmail.vlaskorobogatov.domain.interactor.account

import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.ExpensePreference
import javax.inject.Inject

class SetAccountUseCase @Inject constructor(private val expensePreference: ExpensePreference) :
    UseCase<String, Unit>() {

    override fun execute(parameters: String) {
        expensePreference.setAccountName(parameters)
    }
}