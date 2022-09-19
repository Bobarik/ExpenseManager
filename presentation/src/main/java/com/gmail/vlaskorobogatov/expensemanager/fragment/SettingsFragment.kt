package com.gmail.vlaskorobogatov.expensemanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.activity.MainActivity
import com.gmail.vlaskorobogatov.expensemanager.databinding.FragmentSettingsBinding
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        collectData()
        readyView()

        binding.saveButton.setOnClickListener {
            updateData()
            Toast.makeText(activity, getString(R.string.change_saved), Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        setThemeSwitch()
        return binding.root
    }

    private fun readyView() {
        with(binding) {
            lifecycleScope.launchWhenStarted {
                val x = viewModel.getCurrencies().first()
                operationCurrrencyEdit.setText(viewModel.readCurrency().first())
                operationCurrrencyEdit.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        x
                    )
                )
                appLocaleEdit.setText(viewModel.readLocale())
                appLocaleEdit.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        listOf("Russian", "English")
                    )
                )
            }
        }
    }

    private fun updateData() = with(binding) {
        lifecycleScope.launchWhenStarted {
            val locale = appLocaleEdit.text.toString()
            viewModel.writeLocale(locale)

            val currency = operationCurrrencyEdit.text.toString()
            viewModel.changeCurrency(currency)

            (activity as MainActivity).setAppLocale()
        }
    }

    private fun collectData() = with(binding) {
        lifecycleScope.launchWhenStarted {
            val x = viewModel.readCurrency().first()
            operationCurrrencyEdit.setText(x)
        }
    }

    private fun setThemeSwitch() = with(binding) {
        lifecycleScope.launchWhenCreated {
            themeSwitch.isChecked = viewModel.readTheme() == true

            themeSwitch.setOnCheckedChangeListener { _, nightMode ->
                lifecycleScope.launchWhenStarted {
                    if (nightMode) {
                        viewModel.writeTheme(true)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        viewModel.writeTheme(false)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }
}