package com.gmail.vlaskorobogatov.domain.repostory

interface ExpensePreference {
    fun readTheme(): Boolean

    fun writeTheme(value: Boolean)

    fun readLocale(): String

    fun writeLocale(value: String)

    fun getAccountName(): String

    fun setAccountName(value: String)
}