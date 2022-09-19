package com.gmail.vlaskorobogatov.expensemanager.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.vlaskorobogatov.expensemanager.adapter.AccountAdapter
import com.gmail.vlaskorobogatov.expensemanager.databinding.FragmentAccountDialogBinding
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.AccountListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: AccountListViewModel by viewModels()
    lateinit var binding: FragmentAccountDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AccountAdapter(this, viewModel)
        binding.accountRecyclerView.adapter = adapter

        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.accountRecyclerView.layoutManager = llm

        subscribeUi(adapter)

        binding.addAccountButton.setOnClickListener {
            val action =
                AccountDialogFragmentDirections.accountDialogToAccountAddFragment()
            findNavController().navigate(action)
        }
    }

    private fun subscribeUi(adapter: AccountAdapter) {
        viewModel.accounts.observe(viewLifecycleOwner) { accounts ->
            adapter.setAccountList(accounts)
        }
    }
}