package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import com.gmail.vlaskorobogatov.model.repository.ExpensePreferenceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountListViewModel @Inject internal constructor(
    private val accountRepository: AccountRepository,
    private val currencyRepository: CurrencyRepository,
    private val preferences: ExpensePreferenceImpl
) : ViewModel() {
    val accounts = accountRepository.getAccounts().asLiveData()

    fun changeAccount(account: Account) {
        preferences.setAccountName(account.accountName)
    }

    fun getCurrencies(): Flow<List<String>> {
        return currencyRepository.getCurrencies().map { x -> x.map { y -> y.currencyId } }
    }

    fun insertAccount(account: Account) {
        viewModelScope.launch(Dispatchers.IO) {
            accountRepository.insertAccount(listOf(account))
        }
    }
}