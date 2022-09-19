package com.gmail.vlaskorobogatov.expensemanager.dependency_injection

import android.content.Context
import com.gmail.vlaskorobogatov.model.ExpenseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideExpenseDatabase(@ApplicationContext context: Context): ExpenseDatabase {
        return ExpenseDatabase.getInstance(context)
    }

    @Provides
    fun providesAccountDao(expenseDatabase: ExpenseDatabase) = expenseDatabase.accountDao()

    @Provides
    fun providesOperationDao(expenseDatabase: ExpenseDatabase) = expenseDatabase.operationDao()

    @Provides
    fun providesCurrencyDao(expenseDatabase: ExpenseDatabase) = expenseDatabase.currencyDao()
}