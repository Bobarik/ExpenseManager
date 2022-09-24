package com.gmail.vlaskorobogatov.domain.interactor.settings

import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.ExpensePreference
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(private val expensePreference: ExpensePreference) :
    UseCase<Boolean, Unit>() {

    override fun execute(parameters: Boolean) {
        expensePreference.writeTheme(parameters)
    }
}