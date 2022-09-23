package com.gmail.vlaskorobogatov.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gmail.vlaskorobogatov.domain.OperationCategory

@Entity(
    tableName = "operations",
    foreignKeys = [
        ForeignKey(
            entity = AccountEntity::class,
            parentColumns = ["account_name"],
            childColumns = ["account_name"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class OperationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "operation_id") var operationId: Int = 0,
    val name: String,
    @ColumnInfo(name = "account_name") val accountName: String,
    val amount: Double,
    @ColumnInfo(name = "is_expense") val isExpense: Boolean,
    val date: String,
    val info: String?,
    val category: OperationCategory
)