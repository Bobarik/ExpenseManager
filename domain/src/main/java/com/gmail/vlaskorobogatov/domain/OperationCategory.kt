package com.gmail.vlaskorobogatov.domain

import androidx.annotation.StringRes

data class OperationCategory(
    @StringRes
    val description: Int,
) {
    companion object {

        val Bills = OperationCategory(
            description = R.string.bills
        )

        val Food = OperationCategory(
            description = R.string.food
        )

        val Education = OperationCategory(
            description = R.string.education
        )

        val Entertainment = OperationCategory(
            description = R.string.entertainment
        )

        val Housing = OperationCategory(
            description = R.string.housing
        )

        val Health = OperationCategory(
            description = R.string.health
        )

        val Travel = OperationCategory(
            description = R.string.travel
        )

        val Transportation = OperationCategory(
            description = R.string.transportation
        )

        val Shopping = OperationCategory(
            description = R.string.shopping
        )

        val Salary = OperationCategory(
            description = R.string.salary
        )

        val Investments = OperationCategory(
            description = R.string.investments
        )

        val Other = OperationCategory(
            description = R.string.other
        )

        val CATEGORIES = listOf(
            Bills,
            Food,
            Education,
            Entertainment,
            Housing,
            Travel,
            Transportation,
            Shopping,
            Salary,
            Investments,
            Other
        )
    }
}