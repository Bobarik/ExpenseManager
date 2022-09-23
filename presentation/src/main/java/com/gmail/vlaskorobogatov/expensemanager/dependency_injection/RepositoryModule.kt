package com.gmail.vlaskorobogatov.expensemanager.dependency_injection

import com.gmail.vlaskorobogatov.domain.repostory.AccountRepository
import com.gmail.vlaskorobogatov.domain.repostory.CurrencyRepository
import com.gmail.vlaskorobogatov.domain.repostory.ExpensePreference
import com.gmail.vlaskorobogatov.domain.repostory.OperationRepository
import com.gmail.vlaskorobogatov.model.repository.*
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

    @Binds
    @Singleton
    abstract fun getExpensePreference(preference: ExpensePreferenceImpl): ExpensePreference
}