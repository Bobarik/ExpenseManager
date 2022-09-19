package com.gmail.vlaskorobogatov.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currencies")
data class CurrencyEntity(
    @PrimaryKey
    @ColumnInfo(name = "currency_id") val currencyId: String,
    @ColumnInfo(name = "rate_to_dollar") val rateToDollar: Double = 1.0
)