package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.*
import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.interactor.account.GetAccountNameUseCase
import com.gmail.vlaskorobogatov.domain.interactor.account.GetAccountsUseCase
import com.gmail.vlaskorobogatov.domain.interactor.account.SetAccountUseCase
import com.gmail.vlaskorobogatov.domain.interactor.settings.GetThemeUseCase
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject internal constructor(
    private val operationsRepository: OperationRepository,
    val getThemeUseCase: GetThemeUseCase,
    val setAccountUseCase: SetAccountUseCase,
    getAccountNameUseCase: GetAccountNameUseCase,
    getAccountsUseCase: GetAccountsUseCase
) : ViewModel() {
    val account = MutableLiveData(getAccountNameUseCase(Unit).getOrThrow())
    val period = MutableLiveData(Period.ofYears(5))

    fun readTheme() = getThemeUseCase(Unit).getOrThrow()

    val accounts = getAccountsUseCase(Unit).getOrThrow().asLiveData()

    fun changeAccount(account: String) {
        this.account.value = account
    }

    init {
        account.observeForever { newAccount ->
            setAccountUseCase(newAccount)
        }
    }

    fun getAccountName() = account.value

    fun getFilteredOps(account: String): LiveData<List<Operation>> =
        operationsRepository.getOperations().map { list ->
            list.filter { operation ->
                operation.accountName == account
            }.filter { operation ->
                val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val formattedDate = LocalDate.parse(operation.date, dateFormatter)
                val currentDate = LocalDate.now()

                formattedDate in currentDate.minus(period.value)..currentDate
            }
        }.asLiveData()

    fun deleteOperation(operation: Operation) {
        viewModelScope.launch(Dispatchers.IO) { operationsRepository.delete(listOf(operation)) }
    }

    fun addOperation(operation: Operation) {
        viewModelScope.launch(Dispatchers.IO) {
            operationsRepository.insert(listOf(operation))
        }
    }
}