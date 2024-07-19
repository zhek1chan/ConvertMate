package com.example.convertmate.data.network.dto

data class ConvertDto(
    val amount: String,
    val baseCurrencyCode: String,
    val baseCurrencyName: String,
    var rates: HashMap<String, Rates> = HashMap(),
    val status: String,
    val updatedDate: String
)