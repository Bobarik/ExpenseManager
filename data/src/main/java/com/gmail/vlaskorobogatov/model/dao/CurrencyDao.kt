package com.gmail.vlaskorobogatov.model.dao

import androidx.room.*
import com.gmail.vlaskorobogatov.model.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {
    @Query("SELECT * FROM currencies")
    fun getCurrencies(): Flow<List<CurrencyEntity>>

    @Query("SELECT * FROM currencies WHERE currency_id = :id")
    fun getCurrencyById(id: String): Flow<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currency: List<CurrencyEntity>)

    @Update
    fun update(currency: List<CurrencyEntity>): Int
}