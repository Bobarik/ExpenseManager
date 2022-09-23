package com.gmail.vlaskorobogatov.model.entity.mapper

import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.model.entity.OperationEntity

fun toOperationEntity(operation: Operation): OperationEntity {
    return OperationEntity(
        operation.operationId,
        operation.name,
        operation.accountName,
        operation.amount,
        operation.isExpense,
        operation.date,
        operation.info,
        operation.category
    )
}

fun toOperation(operationEntity: OperationEntity): Operation {
    return Operation(
        operationEntity.operationId,
        operationEntity.name,
        operationEntity.accountName,
        operationEntity.amount,
        operationEntity.isExpense,
        operationEntity.date,
        operationEntity.info,
        operationEntity.category
    )
}

fun toOperationList(operationsCollection: Collection<OperationEntity>): List<Operation> {
    val operationsList = ArrayList<Operation>()
    for (i in operationsCollection) {
        operationsList.add(toOperation(i))
    }
    return operationsList
}

fun toOperationEntityList(operationsCollection: Collection<Operation>): List<OperationEntity> {
    val operationsList = ArrayList<OperationEntity>()
    for (i in operationsCollection) {
        operationsList.add(toOperationEntity(i))
    }
    return operationsList
}