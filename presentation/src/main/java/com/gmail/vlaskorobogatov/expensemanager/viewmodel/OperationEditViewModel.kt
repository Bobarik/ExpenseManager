package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.interactor.account.GetAccountNameUseCase
import com.gmail.vlaskorobogatov.domain.interactor.operation.InsertOperationUseCase
import com.gmail.vlaskorobogatov.domain.interactor.operation.UpdateOperationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperationEditViewModel @Inject internal constructor(
    val insertOperationUseCase: InsertOperationUseCase,
    val updateOperationUseCase: UpdateOperationUseCase,
    getAccountNameUseCase: GetAccountNameUseCase
) : ViewModel() {
    val account = getAccountNameUseCase(Unit).getOrThrow()

    fun addOperation(operation: Operation) {
        viewModelScope.launch(Dispatchers.IO) {
            insertOperationUseCase(operation)
        }
    }

    fun editOperation(operation: Operation) {
        viewModelScope.launch(Dispatchers.IO) {
            updateOperationUseCase(listOf(operation))
        }
    }
}