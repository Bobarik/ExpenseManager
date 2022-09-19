package com.gmail.vlaskorobogatov.domain

import java.io.Serializable

data class Operation(
    val name: String,
    val accountName: String,
    var amount: Double,
    val date: String,
    val info: String?,
    val category: OperationCategory
): Serializable