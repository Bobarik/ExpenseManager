package com.gmail.vlaskorobogatov.domain.repostory

import com.gmail.vlaskorobogatov.domain.Account
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun getAccounts(): Flow<List<Account>>

    fun getAccount(name: String): Flow<Account>

    suspend fun deleteAccount(account: List<Account>)

    suspend fun insertAccount(account: List<Account>)

    suspend fun updateAccount(account: List<Account>)
}