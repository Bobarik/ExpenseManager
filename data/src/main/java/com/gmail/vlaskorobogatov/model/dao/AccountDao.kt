package com.gmail.vlaskorobogatov.model.dao

import androidx.room.*
import com.gmail.vlaskorobogatov.model.entity.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM accounts")
    fun getAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE account_name = :name")
    fun getAccountByName(name: String): Flow<AccountEntity>

    @Insert
    fun insert(accountEntity: List<AccountEntity>)

    @Update
    fun update(accountEntity: List<AccountEntity>)

    @Delete
    fun delete(accountEntity: List<AccountEntity>)
}