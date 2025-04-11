package com.example.udyam.models

data class FinalUserOrder(
    val username: String,
    val address: String,
    val timestamp: String,
    val payment_id: String,
    val items: List<FinalOrderItem>,
    val total: Int
)