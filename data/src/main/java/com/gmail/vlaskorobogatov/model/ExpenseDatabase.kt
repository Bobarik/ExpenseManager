package com.gmail.vlaskorobogatov.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gmail.vlaskorobogatov.model.dao.AccountDao
import com.gmail.vlaskorobogatov.model.dao.CurrencyDao
import com.gmail.vlaskorobogatov.model.dao.OperationDao
import com.gmail.vlaskorobogatov.model.entity.AccountEntity
import com.gmail.vlaskorobogatov.model.entity.CurrencyEntity
import com.gmail.vlaskorobogatov.model.entity.OperationEntity
import com.gmail.vlaskorobogatov.model.entity.converter.CategoryConverter
import java.util.concurrent.Executors

@Database(
    entities = [AccountEntity::class, OperationEntity::class, CurrencyEntity::class],
    version = 2
)
@TypeConverters(CategoryConverter::class)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun operationDao(): OperationDao
    abstract fun currencyDao(): CurrencyDao

    companion object {
        private const val DATABASE_NAME = "Expenses"

        @Volatile
        private var instance: ExpenseDatabase? = null

        fun getInstance(context: Context): ExpenseDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ExpenseDatabase {

            return Room.databaseBuilder(context, ExpenseDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        //pre-populating with main account
                        Executors.newSingleThreadExecutor().execute {
                            instance?.currencyDao()
                                ?.insert(listOf(CurrencyEntity("USD", 1.0)))
                            instance?.accountDao()
                                ?.insert(listOf(AccountEntity("Main", "USD", 0.0)))
                        }
                    }
                }).build()
        }
    }
}