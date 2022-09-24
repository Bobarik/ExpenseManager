package com.gmail.vlaskorobogatov.domain.interactor.operation

import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.interactor.UseCase
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOperationsByAccountUseCase @Inject constructor(
    private val operationRepository: OperationRepository
) : UseCase<String, Flow<List<Operation>>>() {

    override fun execute(parameters: String): Flow<List<Operation>> {
        return operationRepository.getOperationsByAccount(parameters)
    }
}