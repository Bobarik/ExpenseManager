package com.gmail.vlaskorobogatov.domain.repostory

import com.gmail.vlaskorobogatov.domain.Operation
import kotlinx.coroutines.flow.Flow

interface OperationRepository {

    fun getOperations(): Flow<List<Operation>>

    fun getOperationsByAccount(accountName: String): Flow<List<Operation>>

    suspend fun insert(operations: List<Operation>)

    suspend fun update(operations: List<Operation>)

    suspend fun delete(operations: List<Operation>)
}