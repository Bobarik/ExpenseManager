package com.gmail.vlaskorobogatov.domain.interactor.settings

import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.ExpensePreference
import javax.inject.Inject

class SetLocaleUseCase @Inject constructor(private val expensePreference: ExpensePreference) :
    UseCase<String, Unit>() {

    override fun execute(parameters: String) {
        expensePreference.writeLocale(parameters)
    }
}