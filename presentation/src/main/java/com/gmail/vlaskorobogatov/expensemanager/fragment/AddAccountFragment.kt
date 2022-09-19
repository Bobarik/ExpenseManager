package com.gmail.vlaskorobogatov.expensemanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.databinding.FragmentAccountAddBinding
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.AccountListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class AddAccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountAddBinding
    private val viewModel: AccountListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpView()
    }

    private fun setUpView() {
        with(binding) {
            lifecycleScope.launchWhenStarted {
                val x = viewModel.getCurrencies().first()
                accountCurrencyEdit.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        x
                    )
                )
            }
            accountSave.setOnClickListener {
                if (!isFieldMissing()) {
                    insertAccount()
                    view?.findNavController()?.navigateUp()
                }
            }
        }
    }

    private fun isFieldMissing(): Boolean {
        when {
            binding.accountNameEdit.text.isNullOrEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.missing_field),
                    Toast.LENGTH_SHORT
                ).show()
                binding.accountNameEdit.error = getString(R.string.required_field)
            }
            binding.accountCurrencyEdit.text.isNullOrEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.missing_category),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.accountNameEdit.text.isNullOrEmpty() || binding.accountCurrencyEdit.text.isNullOrEmpty()
    }

    private fun insertAccount() {
        viewModel.insertAccount(toOperation())
        Snackbar.make(
            requireView(),
            getString(R.string.save_account),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun toOperation(): Account = binding.let {
        val name = it.accountNameEdit.text.toString()
        val currency = it.accountCurrencyEdit.text.toString()

        return Account(name, currency, 0.0)
    }
}