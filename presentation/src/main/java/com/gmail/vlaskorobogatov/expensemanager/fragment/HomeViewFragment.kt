package com.gmail.vlaskorobogatov.expensemanager.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.adapter.OperationsAdapter
import com.gmail.vlaskorobogatov.expensemanager.databinding.FragmentHomeViewBinding
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.OperationsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.time.Period

@AndroidEntryPoint
class HomeViewFragment : Fragment() {

    private val viewModel: OperationsListViewModel by viewModels()

    private lateinit var binding: FragmentHomeViewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeViewBinding.inflate(inflater, container, false)
        checkTheme()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OperationsAdapter(this)
        subscribeUi(adapter)
        binding.recyclerView.adapter = adapter

        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = llm

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(divider)

        binding.addOperationButton.setOnClickListener {
            view.findNavController().navigate(R.id.home_view_fragment_to_add_operation_fragment)
        }
    }

    private fun subscribeUi(adapter: OperationsAdapter) {
        viewModel.period.observe(viewLifecycleOwner) {
            viewModel.accountOperations.observe(viewLifecycleOwner) { operations ->
                setPeriodFilter()
                val (incomeOps, expenseOps) =
                    operations.partition { it.amount >= 0.0 }

                val income = incomeOps.sumOf { it.amount }
                val expense = expenseOps.sumOf { it.amount }
                val balance = income - expense
                adapter.setOperationsList(operations)
            }
            viewModel.filter()
                .observe(viewLifecycleOwner) { operations ->
                    adapter.setOperationsList(operations)
                    adapter.differ.submitList(operations)
                }
        }
    }

    private fun setPeriodFilter() = with(binding) {
        chipsFilter.filterChipGroup.setOnCheckedStateChangeListener { _, checkedId ->
            if (checkedId.isNotEmpty()) {
                when (checkedId[0]) {
                    chipsFilter.chipAllOperations.id -> viewModel.period.postValue(
                        Period.ofYears(10)
                    )
                    chipsFilter.chipTenDays.id -> viewModel.period.postValue(
                        Period.ofDays(10)
                    )
                    chipsFilter.chipThirtyDays.id -> viewModel.period.postValue(
                        Period.ofDays(30)
                    )
                    chipsFilter.chipSixtyDays.id -> viewModel.period.postValue(
                        Period.ofDays(60)
                    )
                    chipsFilter.chipNinetyDays.id -> viewModel.period.postValue(
                        Period.ofDays(90)
                    )
                    chipsFilter.chipSixMonths.id -> viewModel.period.postValue(
                        Period.ofMonths(6)
                    )
                    else -> viewModel.period.postValue(Period.ofYears(100))
                }
            }
        }
    }

    private fun checkTheme() {
        if (viewModel.readTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}