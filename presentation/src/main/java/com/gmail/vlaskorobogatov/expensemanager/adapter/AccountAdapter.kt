package com.gmail.vlaskorobogatov.expensemanager.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.expensemanager.R
import com.gmail.vlaskorobogatov.expensemanager.databinding.AccountRecyclerViewBinding
import com.gmail.vlaskorobogatov.expensemanager.dialog.AccountDialogFragment
import com.gmail.vlaskorobogatov.expensemanager.viewmodel.AccountListViewModel


class AccountAdapter(private val dialogFragment: AccountDialogFragment, val viewModel: AccountListViewModel) :
    RecyclerView.Adapter<AccountAdapter.AccountViewHolder>() {

    private var accounts: List<Account>? = null

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
        val account = accounts?.get(position)
        holder.bindItems(account!!)
        holder.itemView.setOnClickListener() {
            viewModel.changeAccount(account)
            dialogFragment.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return accounts?.size ?: 0
    }

    fun setAccountList(accountList: List<Account>?) {
        if (accounts == null) {
            accounts = accountList
            notifyItemRangeInserted(0, accountList!!.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return accountList!!.size
                }

                override fun getNewListSize(): Int {
                    return accountList!!.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return TextUtils.equals(
                        accounts!![oldItemPosition].accountName,
                        accounts!![newItemPosition].accountName
                    )
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    val newAccount: Account = accountList!![newItemPosition]
                    val oldAccount: Account = accountList[oldItemPosition]
                    return (TextUtils.equals(newAccount.accountName, oldAccount.accountName)
                            && TextUtils.equals(newAccount.currencyId, newAccount.currencyId)
                            && newAccount.balance == oldAccount.balance)
                }
            })
            accounts = accountList
            result.dispatchUpdatesTo(this)
        }
    }
}