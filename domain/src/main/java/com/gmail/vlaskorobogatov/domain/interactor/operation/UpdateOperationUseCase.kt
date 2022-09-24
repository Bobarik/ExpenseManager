package com.gmail.vlaskorobogatov.domain.interactor.operation

import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.interactor.SuspendUseCase
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import javax.inject.Inject

class UpdateOperationUseCase @Inject constructor(
    private val operationRepository: OperationRepository
) : SuspendUseCase<List<Operation>, Unit>() {

    override suspend fun execute(parameters: List<Operation>) {
        return operationRepository.update(parameters)
    }
}