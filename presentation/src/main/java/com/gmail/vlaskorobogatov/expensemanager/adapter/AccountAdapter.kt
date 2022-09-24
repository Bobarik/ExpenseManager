package com.gmail.vlaskorobogatov.expensemanager.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.databinding.AccountRecyclerViewBinding
import com.gmail.vlaskorobogatov.expensemanager.dialog.AccountDialogFragment
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.HomeFragmentViewModel


class AccountAdapter(private val dialogFragment: AccountDialogFragment, val viewModel: HomeFragmentViewModel) :
    ListAdapter<Account, AccountAdapter.AccountViewHolder>(AccountDiffCallback()) {

    class AccountViewHolder(binding: AccountRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItems(account: Account) {
            val name: TextView = itemView.findViewById(R.id.account_name)

            name.text = account.accountName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return AccountViewHolder(
            AccountRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = getItem(position)
        holder.bindItems(account!!)
        holder.itemView.setOnClickListener() {
            viewModel.changeAccount(account.accountName)
            dialogFragment.dismiss()
        }
    }
}

private class AccountDiffCallback : DiffUtil.ItemCallback<Account>() {

    override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem.accountName == newItem.accountName
    }

    override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
        return oldItem == newItem
    }
}