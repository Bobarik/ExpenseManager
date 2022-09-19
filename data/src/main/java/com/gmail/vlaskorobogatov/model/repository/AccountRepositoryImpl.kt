package com.gmail.vlaskorobogatov.model.repository

import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import com.gmail.vlaskorobogatov.model.dao.AccountDao
import com.gmail.vlaskorobogatov.model.dao.OperationDao
import com.gmail.vlaskorobogatov.model.entity.mapper.toAccountList
import com.gmail.vlaskorobogatov.model.entity.mapper.toAccount
import com.gmail.vlaskorobogatov.model.entity.mapper.toAccountEntityList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepositoryImpl @Inject constructor(private val accountDao: AccountDao) :
    AccountRepository {
    override fun getAccounts(): Flow<List<Account>> {
        return accountDao.getAccounts().map { x -> toAccountList(x) }
    }

    override fun getAccount(name: String) =
        accountDao.getAccountByName(name).map { x -> toAccount(x) }

    override suspend fun deleteAccount(account: List<Account>) =
        accountDao.delete(toAccountEntityList(account))

    override suspend fun insertAccount(account: List<Account>) =
        accountDao.insert(toAccountEntityList(account))

    override suspend fun updateAccount(account: List<Account>) =
        accountDao.update(toAccountEntityList(account))

    companion object {
        @Volatile
        private var instance: OperationRepositoryImpl? = null

        fun getInstance(operationDAO: OperationDao) =
            instance ?: synchronized(this) {
                instance ?: OperationRepositoryImpl(operationDAO).also { instance = it }
            }
    }
}