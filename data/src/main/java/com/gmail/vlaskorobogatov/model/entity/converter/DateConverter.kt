package com.gmail.vlaskorobogatov.model.entity.converter

import androidx.room.TypeConverter
import com.gmail.vlaskorobogatov.domain.OperationCategory
import java.text.SimpleDateFormat
import java.util.*

class CategoryConverter {

    @TypeConverter
    fun fromTransactionCategory(category: OperationCategory): Int {
        return category.description
    }

    @TypeConverter
    fun toTransactionCategory(value: Int): OperationCategory {
        return OperationCategory(value)
    }
}