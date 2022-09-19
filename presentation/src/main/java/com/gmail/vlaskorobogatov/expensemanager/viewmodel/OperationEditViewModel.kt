package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import com.gmail.vlaskorobogatov.model.repository.ExpensePreferenceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperationEditViewModel @Inject internal constructor(
    private val operationsRepository: OperationRepository,
    private val preference: ExpensePreferenceImpl
) : ViewModel() {
    var account = preference.getAccountName()

    fun addOperation(operation: Operation) {
        viewModelScope.launch(Dispatchers.IO) {
            operationsRepository.insert(listOf(operation))
        }
    }

    fun editOperation(operation: Operation) {
        viewModelScope.launch(Dispatchers.IO) {
            operationsRepository.update(listOf(operation))
        }
    }
}