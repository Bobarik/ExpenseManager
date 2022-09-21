package com.gmail.vlaskorobogatov.expensemanager.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.adapter.OperationsAdapter
import com.gmail.vlaskorobogatov.expensemanager.databinding.FragmentHomeViewBinding
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.HomeFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.Period

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeFragmentViewModel by viewModels()
    lateinit var adapter: OperationsAdapter
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

        adapter = OperationsAdapter(this)
        subscribeUi(adapter)
        binding.recyclerView.adapter = adapter
        swipeToDelete(adapter)

        val llm = LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = llm

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding.recyclerView.addItemDecoration(divider)

        binding.addOperationButton.setOnClickListener {
            view.findNavController().navigate(R.id.home_view_fragment_to_add_operation_fragment)
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.top_menu, menu)
                lifecycleScope.launch {
                    menu.getItem(0).title = viewModel.getAccountName()
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.account_dialog -> {
                        findNavController().navigate(HomeFragmentDirections.homeViewFragmentToAccountDialog())
                        true
                    }
                    else -> menuItem.onNavDestinationSelected(findNavController())
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.changeAccount(viewModel.preferences.getAccountName())
    }

    private fun subscribeUi(adapter: OperationsAdapter) {
        viewModel.account.observe(viewLifecycleOwner) { account ->
            viewModel.period.observe(viewLifecycleOwner) {
                viewModel.getFilteredOps(account).observe(viewLifecycleOwner) { operationList ->
                    setPeriodFilter()
                    val (incomeOps, expenseOps) =
                        operationList.partition { it.amount >= 0.0 }

                    val income = incomeOps.sumOf { it.amount }
                    val expense = expenseOps.sumOf { it.amount }
                    val balance = income - expense
                    adapter.submitList(operationList)
                }
            }
        }
    }

    private fun setPeriodFilter() = with(binding) {
        chipsFilter.filterChipGroup.setOnCheckedStateChangeListener { _, checkedId ->
            if (checkedId.isNotEmpty()) {
                when (checkedId[0]) {
                    chipsFilter.chipAllOperations.id -> viewModel.period.value = Period.ofYears(10)

                    chipsFilter.chipTenDays.id -> viewModel.period.value = Period.ofDays(10)

                    chipsFilter.chipThirtyDays.id -> viewModel.period.value = Period.ofDays(30)

                    chipsFilter.chipSixtyDays.id -> viewModel.period.value = Period.ofDays(60)

                    chipsFilter.chipNinetyDays.id -> viewModel.period.value = Period.ofDays(90)

                    chipsFilter.chipSixMonths.id -> viewModel.period.value = Period.ofMonths(6)

                    else -> viewModel.period.value = Period.ofYears(100)
                }
            }
        }
    }

    private fun swipeToDelete(adapter: OperationsAdapter) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val operation = adapter.currentList[position]
                viewModel.deleteOperation(operation)

                Snackbar.make(
                    binding.root,
                    getString(R.string.transaction_deleted),
                    Snackbar.LENGTH_SHORT
                ).apply {
                    setAction(getString(R.string.undo)) {
                        viewModel.addOperation(operation)
                    }
                }.show()

            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)
    }

    private fun checkTheme() {
        if (viewModel.readTheme()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}