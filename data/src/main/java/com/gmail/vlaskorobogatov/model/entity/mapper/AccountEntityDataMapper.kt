package com.gmail.vlaskorobogatov.model.entity.mapper

import com.gmail.vlaskorobogatov.domain.Account
import com.gmail.vlaskorobogatov.model.entity.AccountEntity

fun toAccount(accountEntity: AccountEntity): Account {
    return Account(accountEntity.accountName, accountEntity.currencyId, accountEntity.balance)
}

fun toAccountEntity(account: Account): AccountEntity {
    return AccountEntity(account.accountName, account.currencyId, account.balance)
}

fun toAccountList(accountsCollection: Collection<AccountEntity>): List<Account> {
    val accountList = ArrayList<Account>()
    for(i in accountsCollection) {
        accountList.add(toAccount(i))
    }
    return accountList
}

fun toAccountEntityList(accountsCollection: Collection<Account>): List<AccountEntity> {
    val accountEntityList = ArrayList<AccountEntity>()
    for(i in accountsCollection) {
        accountEntityList.add(toAccountEntity(i))
    }
    return accountEntityList
}
