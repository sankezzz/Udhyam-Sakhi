package com.example.udyam.models

data class UserOrder(
    val username: String = "",
    val address: String = "",
    val timestamp: String = "",
    val payment_id: String = "",
    val items: List<OrderItem> = emptyList(),
    val total: Int = 0
)
