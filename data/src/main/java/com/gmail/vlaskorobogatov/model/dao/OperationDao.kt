package com.gmail.vlaskorobogatov.model.dao

import androidx.room.*
import com.gmail.vlaskorobogatov.model.entity.OperationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OperationDao {
    @Query("SELECT * FROM operations")
    fun getAll(): Flow<List<OperationEntity>>

    @Query("SELECT * FROM operations WHERE :accountName = account_name")
    fun getOpsByAccount(accountName: String): Flow<List<OperationEntity>>

    @Insert
    fun insert(operationEntity: List<OperationEntity>)

    @Delete
    fun delete(operationEntity: List<OperationEntity>)

    @Update
    fun update(operationEntity: List<OperationEntity>)
}