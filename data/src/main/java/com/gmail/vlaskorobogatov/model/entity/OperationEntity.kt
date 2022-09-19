package com.gmail.vlaskorobogatov.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.gmail.vlaskorobogatov.domain.OperationCategory
import java.util.Date

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
    val name: String,
    @ColumnInfo(name = "account_name") val accountName: String,
    val amount: Double,
    val date: String,
    val info: String?,
    val category: OperationCategory
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "operation_id") var operationId: Int = 0
}