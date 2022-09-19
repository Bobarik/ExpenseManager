package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.gmail.vlaskorobogatov.model.repository.ExpensePreferenceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject internal constructor(val preferences: ExpensePreferenceImpl):ViewModel() {

    fun readLocale(): Locale {
        println(preferences.readLocale())
        return when(preferences.readLocale()) {
            "Russian" -> Locale("ru")
            "English" -> Locale("en")
            else -> {Locale("en")}
        }

    }

    fun readTheme(): Boolean {
        return preferences.readTheme()
    }
}