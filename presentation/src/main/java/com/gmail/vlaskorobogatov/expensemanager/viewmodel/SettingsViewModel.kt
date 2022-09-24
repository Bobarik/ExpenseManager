package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.*
import com.gmail.vlaskorobogatov.domain.interactor.account.GetAccountCurrencyUseCase
import com.gmail.vlaskorobogatov.domain.interactor.settings.GetLocaleUseCase
import com.gmail.vlaskorobogatov.domain.interactor.settings.GetThemeUseCase
import com.gmail.vlaskorobogatov.domain.interactor.settings.SetLocaleUseCase
import com.gmail.vlaskorobogatov.domain.interactor.settings.SetThemeUseCase
import com.gmail.vlaskorobogatov.domain.interactor.account.GetAccountNameUseCase
import com.gmail.vlaskorobogatov.domain.interactor.account.GetAccountUseCase
import com.gmail.vlaskorobogatov.domain.interactor.account.UpdateAccountUseCase
import com.gmail.vlaskorobogatov.domain.interactor.currency.GetCurrenciesUseCase
import com.gmail.vlaskorobogatov.domain.interactor.currency.GetCurrencyUseCase
import com.gmail.vlaskorobogatov.domain.interactor.currency.UpdateCurrenciesUseCase
import com.gmail.vlaskorobogatov.domain.interactor.operation.GetOperationsByAccountUseCase
import com.gmail.vlaskorobogatov.domain.interactor.operation.UpdateOperationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject internal constructor(
    private val setLocaleUseCase: SetLocaleUseCase,
    private val getLocaleUseCase: GetLocaleUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getAccountCurrencyUseCase: GetAccountCurrencyUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    getAccountNameUseCase: GetAccountNameUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val updateCurrenciesUseCase: UpdateCurrenciesUseCase,
    private val getOperationsByAccountUseCase: GetOperationsByAccountUseCase,
    private val updateOperationUseCase: UpdateOperationUseCase
) : ViewModel() {
    private var currentAccount = MutableLiveData(getAccountNameUseCase(Unit).getOrThrow())

    fun readTheme(): Boolean {
        return getThemeUseCase(Unit).getOrThrow()
    }

    fun writeTheme(value: Boolean) {
        setThemeUseCase(value)
    }

    fun readLocale(): String {
        return getLocaleUseCase(Unit).getOrThrow()
    }

    fun writeLocale(value: String) {
        setLocaleUseCase(value)
    }

    fun readCurrency(): Flow<String> {
        return getAccountCurrencyUseCase(currentAccount.value!!).getOrThrow()
    }

    fun getCurrencies(): LiveData<List<String>> {
        viewModelScope.launch(Dispatchers.IO) {
            updateCurrenciesUseCase(Unit)
        }

        return getCurrenciesUseCase(Unit).getOrThrow().map { x -> x.map { y -> y.currencyId } }
            .asLiveData()
    }

    suspend fun changeCurrency(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val account = getAccountUseCase(currentAccount.value!!).getOrThrow().first()
            val operations =
                getOperationsByAccountUseCase(currentAccount.value!!).getOrThrow().first()
            val rateFrom = getCurrencyUseCase(account.currencyId).getOrThrow().first().rateToDollar
            val rateTo = getCurrencyUseCase(value).getOrThrow().first().rateToDollar
            for (op in operations) {
                op.amount = op.amount / rateFrom * rateTo
            }

            account.currencyId = value
            updateOperationUseCase(operations)
            updateAccountUseCase(account)
        }
    }
}