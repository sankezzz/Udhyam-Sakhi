package com.example.udyam.models

data class OrderResponse(
    val address: String,
    val items: List<OrderItem>,
    val payment_id: String,
    val timestamp: String,
    val total: Int,
    val username: String
)