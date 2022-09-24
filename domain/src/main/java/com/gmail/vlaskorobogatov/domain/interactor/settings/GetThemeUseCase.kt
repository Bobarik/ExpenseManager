package com.gmail.vlaskorobogatov.domain.interactor.settings

import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.ExpensePreference
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(private val expensePreference: ExpensePreference) :
    UseCase<Unit, Boolean>() {

    override fun execute(parameters: Unit): Boolean {
        return expensePreference.readTheme()
    }
}