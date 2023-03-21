package com.example.binapp.model

data class BankModel(
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: String,
    val alpha2: String,
    val c_name: String,
    val latitude: String,
    val longitude: String,
    var b_name: String,
    var url: String,
    var phone: String,
    var city: String,
)
