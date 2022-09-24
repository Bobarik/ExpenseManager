package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.interactor.account.GetAccountNameUseCase
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperationEditViewModel @Inject internal constructor(
    private val operationsRepository: OperationRepository,
    getAccountNameUseCase: GetAccountNameUseCase
) : ViewModel() {
    val account = getAccountNameUseCase(Unit).getOrThrow()

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