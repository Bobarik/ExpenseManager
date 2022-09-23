package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.*
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import com.gmail.vlaskorobogatov.model.repository.ExpensePreferenceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject internal constructor(
    private val preferences: ExpensePreferenceImpl,
    private val accountRepository: AccountRepository,
    private val currencyRepository: CurrencyRepository,
    private val operationRepository: OperationRepository,
) : ViewModel() {
    private var currentAccount = MutableLiveData(preferences.getAccountName())

    fun readTheme(): Boolean {
        return preferences.readTheme()
    }

    fun writeTheme(value: Boolean) {
        preferences.writeTheme(value)
    }

    fun readLocale(): String {
        return preferences.readLocale()
    }

    fun writeLocale(value: String) {
        preferences.writeLocale(value)
    }

    fun readCurrency(): Flow<String> {
        return accountRepository.getAccount(currentAccount.value!!).map { x -> x.currencyId }
    }

    fun getCurrencies(): LiveData<List<String>> {
        viewModelScope.launch(Dispatchers.IO) {
            currencyRepository.updateCurrencies()
        }

        return currencyRepository.getCurrencies().map { x -> x.map { y -> y.currencyId } }.asLiveData()
    }

    suspend fun changeCurrency(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val account = accountRepository.getAccount(currentAccount.value!!).first()
            val operations =
                operationRepository.getOperationsByAccount(currentAccount.value!!).first()
            val rateFrom =
                currencyRepository.getCurrencyById(account.currencyId).first().rateToDollar
            val rateTo = currencyRepository.getCurrencyById(value).first().rateToDollar
            for (op in operations) {
                op.amount = op.amount / rateFrom * rateTo
            }

            account.currencyId = value
            operationRepository.update(operations)
            accountRepository.updateAccount(listOf(account))
        }
    }
}