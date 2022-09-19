package com.gmail.vlaskorobogatov.expensemanager.dependency_injection

import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import com.gmail.vlaskorobogatov.model.repository.AccountRepositoryImpl
import com.gmail.vlaskorobogatov.model.repository.CurrencyNetworkRepository
import com.gmail.vlaskorobogatov.model.repository.CurrencyRoomRepositoryImpl
import com.gmail.vlaskorobogatov.model.repository.OperationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun getAccountRepository(repo: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun getCurrencyRepository(repo: CurrencyNetworkRepository): CurrencyRepository

    @Binds
    @Singleton
    abstract fun getOperationRepository(repo: OperationRepositoryImpl): OperationRepository
}