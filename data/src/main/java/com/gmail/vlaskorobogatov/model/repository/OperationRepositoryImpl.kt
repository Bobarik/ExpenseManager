package com.gmail.vlaskorobogatov.model.repository

import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import com.gmail.vlaskorobogatov.model.dao.OperationDao
import com.gmail.vlaskorobogatov.model.entity.mapper.toOperationEntityList
import com.gmail.vlaskorobogatov.model.entity.mapper.toOperationList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OperationRepositoryImpl @Inject constructor(private val operationDAO: OperationDao) :
    OperationRepository {
    override fun getOperations(): Flow<List<Operation>> {
        return operationDAO.getAll().map { x -> toOperationList(x) }
    }

    override fun getOperationsByAccount(accountName: String): Flow<List<Operation>> {
        return operationDAO.getOpsByAccount(accountName).map { x -> toOperationList(x) }
    }

    override suspend fun insert(operations: List<Operation>) =
        operationDAO.insert(toOperationEntityList(operations))

    override suspend fun update(operations: List<Operation>) =
        operationDAO.update(toOperationEntityList(operations))

    override suspend fun delete(operations: List<Operation>) =
        operationDAO.delete(toOperationEntityList(operations))

    companion object {
        @Volatile
        private var instance: OperationRepositoryImpl? = null

        fun getInstance(operationDAO: OperationDao) =
            instance ?: synchronized(this) {
                instance ?: OperationRepositoryImpl(operationDAO).also { instance = it }
            }
    }
}