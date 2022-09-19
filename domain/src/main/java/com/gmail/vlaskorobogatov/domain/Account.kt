package com.gmail.vlaskorobogatov.domain

import java.io.Serializable

data class Account(
    val accountName: String,
    var currencyId: String,
    val balance: Double
): Serializable