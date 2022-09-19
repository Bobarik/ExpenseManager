package com.gmail.vlaskorobogatov.expensemanager.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.vlaskorobogatov.domain.Operation
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.databinding.OperationRecyclerViewBinding
import com.gmail.vlaskorobogatov.expensemanager.fragment.HomeViewFragment
import com.gmail.vlaskorobogatov.expensemanager.fragment.HomeViewFragmentDirections


class OperationsAdapter(private val fragment: HomeViewFragment) :
    RecyclerView.Adapter<OperationsAdapter.OperationViewHolder>() {

    private var operationsList: List<Operation>? = null

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
        val operation = operationsList?.get(position)
        holder.bindItems(operation!!, fragment.requireContext())
        holder.itemView.setOnClickListener {
            val action =
                HomeViewFragmentDirections.homeViewFragmentToEditOperationFragment(operation)
            fragment.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return operationsList?.size ?: 0
    }

    private val differCallback = object : DiffUtil.ItemCallback<Operation>() {
        override fun areItemsTheSame(
            oldItem: Operation,
            newItem: Operation
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Operation,
            newItem: Operation
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    fun setOperationsList(newList: List<Operation>?) {
        if (operationsList == null) {
            operationsList = newList
            notifyItemRangeInserted(0, newList!!.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return operationsList!!.size
                }

                override fun getNewListSize(): Int {
                    return newList!!.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return oldItemPosition == newItemPosition
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val newOperation: Operation = newList!![newItemPosition]
                    val oldOperation: Operation = operationsList!![oldItemPosition]
                    return (TextUtils.equals(newOperation.name, oldOperation.name)
                            && TextUtils.equals(newOperation.info, oldOperation.info)
                            && TextUtils.equals(newOperation.accountName, oldOperation.accountName)
                            && newOperation.date == oldOperation.date
                            && newOperation.amount == oldOperation.amount)
                            && newOperation.category == oldOperation.category
                }
            })
            operationsList = newList
            result.dispatchUpdatesTo(this)
        }
    }
}