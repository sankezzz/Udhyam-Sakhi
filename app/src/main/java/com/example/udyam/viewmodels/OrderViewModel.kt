package com.example.udyam.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.udyam.RetrofitInstance
import com.example.udyam.models.OrderNotification
import com.example.udyam.utils.OrderHistoryManager
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    fun fetchLatestOrderOnce() {
        viewModelScope.launch {
            try {
                val order = RetrofitInstance.api.getLatestOrder()
                val item = order.items.firstOrNull()

                if (item != null) {
                    val orderNotif = OrderNotification(
                        timestamp = order.timestamp,
                        itemName = item.name,
                        amount = "₹${item.price}",
                        address = order.address,
                        username = order.username
                    )
                    // Avoid adding duplicate payment_id
                    OrderHistoryManager.addOrder(orderNotif, order.payment_id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
