package com.gmail.vlaskorobogatov.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    foreignKeys = [ForeignKey(
        entity = CurrencyEntity::class,
        parentColumns = ["currency_id"],
        childColumns = ["currency_id"],
        onDelete = ForeignKey.NO_ACTION
    )]
)
data class AccountEntity(
    @PrimaryKey
    @ColumnInfo(name = "account_name") val accountName: String,
    @ColumnInfo(name = "currency_id") val currencyId: String,
    val balance: Double
)
