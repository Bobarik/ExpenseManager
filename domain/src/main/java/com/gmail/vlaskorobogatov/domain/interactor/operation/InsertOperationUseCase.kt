package com.gmail.vlaskorobogatov.domain.interactor.operation

import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.interactor.SuspendUseCase
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import javax.inject.Inject

class InsertOperationUseCase @Inject constructor(
    private val operationRepository: OperationRepository
) : SuspendUseCase<Operation, Unit>() {

    override suspend fun execute(parameters: Operation) {
        return operationRepository.insert(listOf(parameters))
    }
}