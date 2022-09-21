package com.gmail.vlaskorobogatov.expensemanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.databinding.OperationRecyclerViewBinding
import com.gmail.vlaskorobogatov.expensemanager.fragment.HomeFragment
import com.gmail.vlaskorobogatov.expensemanager.fragment.HomeFragmentDirections


class OperationsAdapter(val fragment: HomeFragment) :
    ListAdapter<Operation, OperationsAdapter.OperationViewHolder>(OperationDiffCallback()) {

    class OperationViewHolder(binding: OperationRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(operation: Operation, context: Context) {
            val date: TextView = itemView.findViewById(R.id.operation_date)
            val category: TextView = itemView.findViewById(R.id.operation_category)
            val amount: TextView = itemView.findViewById(R.id.operation_amount)
            val name: TextView = itemView.findViewById(R.id.operation_name)

            date.text = operation.date
            category.text = context.getString(operation.category.description)
            amount.text = operation.amount.toString()
            name.text = operation.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        return OperationViewHolder(
            OperationRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: OperationViewHolder, position: Int) {
        val operation = getItem(position)
        holder.bindItems(operation!!, fragment.requireContext())
        holder.itemView.setOnClickListener {
            val action =
                HomeFragmentDirections.homeViewFragmentToEditOperationFragment(operation)
            fragment.findNavController().navigate(action)
        }
    }
}


private class OperationDiffCallback : DiffUtil.ItemCallback<Operation>() {

    override fun areItemsTheSame(oldItem: Operation, newItem: Operation): Boolean {
        return oldItem.operationId == newItem.operationId
    }

    override fun areContentsTheSame(oldItem: Operation, newItem: Operation): Boolean {
        return oldItem == newItem
    }
}