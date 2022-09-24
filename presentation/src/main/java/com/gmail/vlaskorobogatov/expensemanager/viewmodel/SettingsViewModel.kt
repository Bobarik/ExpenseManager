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
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject internal constructor(
    private val currencyRepository: CurrencyRepository,
    private val operationRepository: OperationRepository,
    private val setLocaleUseCase: SetLocaleUseCase,
    private val getLocaleUseCase: GetLocaleUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val getThemeUseCase: GetThemeUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val getAccountCurrencyUseCase: GetAccountCurrencyUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase,
    getAccountNameUseCase: GetAccountNameUseCase,
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
            currencyRepository.updateCurrencies()
        }

        return currencyRepository.getCurrencies().map { x -> x.map { y -> y.currencyId } }
            .asLiveData()
    }

    suspend fun changeCurrency(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val account = getAccountUseCase(currentAccount.value!!).getOrThrow().first()
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
            updateAccountUseCase(account)
        }
    }
}