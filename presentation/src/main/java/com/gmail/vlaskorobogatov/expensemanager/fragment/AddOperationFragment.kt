package com.gmail.vlaskorobogatov.expensemanager.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.domain.OperationCategory
import com.gmail.vlaskorobogatov.domain.R.string
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.databinding.FragmentOperationDetailsEditBinding
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.OperationEditViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Double.parseDouble
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddOperationFragment : Fragment() {
    private lateinit var binding: FragmentOperationDetailsEditBinding
    private val viewModel: OperationEditViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOperationDetailsEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpView()
    }

    private fun setUpView() {
        with(binding) {
            operationCategoryEdit.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    OperationCategory.CATEGORIES.map { x ->
                        getString(x.description)
                    }
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

            operationSave.setOnClickListener {
                if (!isFieldMissing()) {
                    insertOperation()
                    view?.findNavController()?.navigateUp()
                }
            }
        }
    }

    private fun isFieldMissing(): Boolean {

        when {
            binding.operationAmountEdit.text.isNullOrEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.missing_field),
                    Toast.LENGTH_SHORT
                ).show()
                binding.operationAmountEdit.error = getString(R.string.required_field)

            }
            binding.operationNameEdit.text.isNullOrEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.missing_field),
                    Toast.LENGTH_SHORT
                ).show()
                binding.operationNameEdit.error = getString(R.string.required_field)
            }
            binding.operationCategoryEdit.text.isNullOrEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.missing_category),
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.operationDateEdit.text.isNullOrEmpty() -> {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.invalid_date),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return with(binding) {
            operationAmountEdit.text.isNullOrEmpty() ||
                    operationDateEdit.text.isNullOrEmpty() ||
                    operationNameEdit.text.isNullOrEmpty() ||
                    operationCategoryEdit.text.isNullOrEmpty()
        }
    }

    private fun insertOperation() {
        viewModel.addOperation(toOperation())
        Snackbar.make(
            requireView(),
            getString(R.string.operation_saved),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun toOperation(): Operation = binding.let {
        val name = it.operationNameEdit.text.toString()
        val amount = parseDouble(it.operationAmountEdit.text.toString())
        val date = it.operationDateEdit.text.toString()
        val category = when (it.operationCategoryEdit.text.toString()) {
            getString(string.bills) -> OperationCategory.Bills
            getString(string.food) -> OperationCategory.Food
            getString(string.education) -> OperationCategory.Education
            getString(string.entertainment) -> OperationCategory.Entertainment
            getString(string.housing) -> OperationCategory.Housing
            getString(string.health) -> OperationCategory.Health
            getString(string.travel) -> OperationCategory.Travel
            getString(string.transportation) -> OperationCategory.Transportation
            getString(string.shopping) -> OperationCategory.Shopping
            getString(string.salary) -> OperationCategory.Salary
            getString(string.investments) -> OperationCategory.Investments
            getString(string.other) -> OperationCategory.Other
            else -> OperationCategory.Other
        }
        val info = it.operationInfoEdit.text.toString()

        return Operation(
            name = name,
            amount = amount,
            category = category,
            date = date,
            info = info,
            accountName = viewModel.account
        )
    }
}