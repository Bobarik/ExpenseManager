package com.gmail.vlaskorobogatov.model.entity

import com.google.gson.annotations.SerializedName


data class CurrencyApiResponse(
    @field:SerializedName("disclaimer") val disclaimer: String,
    @field:SerializedName("license") val license: String,
    @field:SerializedName("timestamp") val timestamp: Long,
    @field:SerializedName("base") val base:String,
    @field:SerializedName("rates") val rates: Map<String, Double>
    )