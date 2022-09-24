package com.gmail.vlaskorobogatov.domain.interactor.operation

import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOperationsUseCase @Inject constructor(
    private val operationRepository: OperationRepository
) : UseCase<Unit, Flow<List<Operation>>>() {

    override fun execute(parameters: Unit): Flow<List<Operation>> {
        return operationRepository.getOperations()
    }
}