package com.example.udyam.models

data class OrderNotification(
    val timestamp: String,
    val itemName: String,
    val amount: String,
    val address: String,
    val username: String
)

