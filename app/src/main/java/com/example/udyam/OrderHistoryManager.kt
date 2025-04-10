package com.example.udyam.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.udyam.models.OrderNotification

object OrderHistoryManager {
    private val _orderHistory = MutableLiveData<MutableList<OrderNotification>>(mutableListOf())
    val orderHistory: LiveData<MutableList<OrderNotification>> = _orderHistory

    private val seenPaymentIds = mutableSetOf<String>()

    fun addOrder(order: OrderNotification, paymentId: String) {
        if (!seenPaymentIds.contains(paymentId)) {
            seenPaymentIds.add(paymentId)
            val current = _orderHistory.value ?: mutableListOf()
            current.add(0, order) // add latest at top
            _orderHistory.postValue(current)
        }
    }
}
