package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.domain.interactor.account.InsertAccountUseCase
import com.gmail.vlaskorobogatov.domain.interactor.currency.GetCurrenciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountListViewModel @Inject internal constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val insertAccountUseCase: InsertAccountUseCase
) : ViewModel() {

    fun getCurrencies(): Flow<List<String>> {
        return getCurrenciesUseCase(Unit).getOrThrow().map { x -> x.map { y -> y.currencyId } }
    }

    fun insertAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            insertAccountUseCase(account)
        }
    }
}