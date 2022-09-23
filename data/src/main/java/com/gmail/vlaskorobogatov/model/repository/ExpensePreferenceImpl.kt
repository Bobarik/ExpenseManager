package com.gmail.vlaskorobogatov.model.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.gmail.vlaskorobogatov.domain.repostory.ExpensePreference
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpensePreferenceImpl @Inject constructor(@ApplicationContext context: Context): ExpensePreference{
    private val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun readTheme(): Boolean {
        return preference.getBoolean("dark_theme", false)
    }

    override fun writeTheme(value: Boolean) {
        preference.edit().putBoolean("dark_theme", value).apply()
    }

    override fun readLocale(): String {
        return preference.getString("locale", "English")!!
    }

    override fun writeLocale(value: String) {
        preference.edit().putString("locale", value).apply()
    }

    fun getAccountName(): String {
        return preference.getString("account", "Main")!!
    }

    fun setAccountName(value: String) {
        preference.edit().putString("account", value).apply()
    }
}