package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.*
import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import com.gmail.vlaskorobogatov.model.repository.ExpensePreferenceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class OperationsListViewModel @Inject internal constructor(
    private val operationsRepository: OperationRepository,
    private val accountRepository: AccountRepository,
    private val preferences: ExpensePreferenceImpl,
) : ViewModel() {
    var accountName = preferences.getAccountName()
    var account: String = preferences.getAccountName()

    private val _period = MutableLiveData(Period.ofYears(5))
    var period = _period

    fun readTheme(): Boolean {
        return preferences.readTheme()
    }

    fun filter(): LiveData<List<Operation>> =
        if (period.value != null) {
            accountOperations.map { list ->
                list.filter { transaction ->
                    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                    val formattedDate = LocalDate.parse(transaction.date, dateFormatter)
                    val currentDate = LocalDate.now()

                    formattedDate in currentDate.minus(period.value)..currentDate
                }
            }
        } else accountOperations


    fun getAccountFromName(value: String) = accountRepository.getAccount(value)

    var accountOperations = operationsRepository.getOperationsByAccount(accountName).asLiveData()
}