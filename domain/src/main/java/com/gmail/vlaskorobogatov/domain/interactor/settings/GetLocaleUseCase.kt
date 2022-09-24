package com.gmail.vlaskorobogatov.domain.interactor.settings

import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.ExpensePreference
import javax.inject.Inject

class GetLocaleUseCase @Inject constructor(private val expensePreference: ExpensePreference) :
    UseCase<Unit, String>() {

    override fun execute(parameters: Unit): String {
        return expensePreference.readLocale()
    }
}