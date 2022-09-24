package com.gmail.vlaskorobogatov.expensemanager.viewmodel

import androidx.lifecycle.ViewModel
import com.gmail.vlaskorobogatov.domain.interactor.settings.GetLocaleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject internal constructor(
    val getLocaleUseCase: GetLocaleUseCase
) : ViewModel() {

    fun readLocale(): Locale {
        println(getLocaleUseCase(Unit).getOrThrow())
        return when (getLocaleUseCase(Unit).getOrThrow()) {
            "Russian" -> Locale("ru")
            "English" -> Locale("en")
            else -> {
                Locale("en")
            }
        }
    }
}