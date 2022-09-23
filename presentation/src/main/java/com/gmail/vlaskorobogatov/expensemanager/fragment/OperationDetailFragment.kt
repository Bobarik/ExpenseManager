package com.gmail.vlaskorobogatov.expensemanager.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.OperationCategory
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.databinding.FragmentOperationDetailsEditBinding
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.OperationEditViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.reflect.typeOf

@AndroidEntryPoint
class OperationDetailFragment : Fragment() {

    private val viewModel: OperationEditViewModel by viewModels()
    private lateinit var binding: FragmentOperationDetailsEditBinding
    private lateinit var operation: Operation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOperationDetailsEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpView()
    }

    private fun setUpView() {
        with(binding) {
            operation = arguments?.get("operation") as Operation
            operationAmountEdit.setText(operation.amount.toString())
            operationCategoryEdit.setText(getString(operation.category.description))
            operationNameEdit.setText(operation.name)
            operationInfoEdit.setText(operation.info)
            operationDateEdit.setText(operation.date)

            operationCategoryEdit.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    OperationCategory.CATEGORIES.map { x ->
                        getString(x.description)
                    }
                )
            )

            operationTypeEdit.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    listOf(getString(R.string.expense), getString(R.string.income))
                )
            )

            operationDateEdit.apply {
                isClickable = true
                isFocusable = true
                isFocusableInTouchMode = false
                val myCalendar = Calendar.getInstance()
                val dateFormat =
                    SimpleDateFormat(context.getString(R.string.date_format), Locale.UK)
                val datePickerOnDateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        myCalendar.set(Calendar.YEAR, year)
                        myCalendar.set(Calendar.MONTH, monthOfYear)
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        setText(dateFormat.format(myCalendar.time))
                    }

                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    datePickerOnDateSetListener,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)
                )

                datePickerDialog.datePicker.maxDate = Date().time
                setOnClickListener {
                    datePickerDialog.show()
                }
            }

            binding.operationSave.setOnClickListener {
                updateOperation()
                view?.findNavController()?.navigateUp()
            }
        }
    }

    private fun updateOperation() {
        viewModel.editOperation(toOperation())
        Snackbar.make(
            requireView(),
            getString(R.string.operation_saved),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun toOperation(): Operation = binding.let {
        val name = it.operationNameEdit.text.toString()
        val amount = it.operationAmountEdit.text.toString().toDouble()
        val date = it.operationDateEdit.text.toString()
        val category = when (it.operationCategoryEdit.text.toString()) {
            getString(com.gmail.vlaskorobogatov.domain.R.string.bills) -> OperationCategory.Bills
            getString(com.gmail.vlaskorobogatov.domain.R.string.food) -> OperationCategory.Food
            getString(com.gmail.vlaskorobogatov.domain.R.string.education) -> OperationCategory.Education
            getString(com.gmail.vlaskorobogatov.domain.R.string.entertainment) -> OperationCategory.Entertainment
            getString(com.gmail.vlaskorobogatov.domain.R.string.housing) -> OperationCategory.Housing
            getString(com.gmail.vlaskorobogatov.domain.R.string.health) -> OperationCategory.Health
            getString(com.gmail.vlaskorobogatov.domain.R.string.travel) -> OperationCategory.Travel
            getString(com.gmail.vlaskorobogatov.domain.R.string.transportation) -> OperationCategory.Transportation
            getString(com.gmail.vlaskorobogatov.domain.R.string.shopping) -> OperationCategory.Shopping
            getString(com.gmail.vlaskorobogatov.domain.R.string.salary) -> OperationCategory.Salary
            getString(com.gmail.vlaskorobogatov.domain.R.string.investments) -> OperationCategory.Investments
            getString(com.gmail.vlaskorobogatov.domain.R.string.other) -> OperationCategory.Other
            else -> OperationCategory.Other
        }
        val isExpense = (it.operationTypeEdit.text.toString() == "Expense")
        val info = it.operationInfoEdit.text.toString()

        return Operation(
            operationId = operation.operationId,
            name = name,
            amount = amount,
            category = category,
            isExpense = isExpense,
            date = date,
            info = info,
            accountName = viewModel.account
        )
    }
}