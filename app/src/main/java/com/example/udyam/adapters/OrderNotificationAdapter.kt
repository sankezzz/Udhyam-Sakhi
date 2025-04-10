package com.example.udyam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.udyam.R
import com.example.udyam.models.OrderNotification

class OrderNotificationAdapter(
    private var orderList: List<OrderNotification>
) : RecyclerView.Adapter<OrderNotificationAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val status = itemView.findViewById<TextView>(R.id.orderStatus)
        val itemName = itemView.findViewById<TextView>(R.id.orderItemName)
        val amount = itemView.findViewById<TextView>(R.id.orderAmount)
        val address = itemView.findViewById<TextView>(R.id.orderAddress)
        val username = itemView.findViewById<TextView>(R.id.orderUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_notification, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.status.text = "Ordered on ${order.timestamp}"
        holder.itemName.text = "Item: ${order.itemName}"
        holder.amount.text = "Amount: ${order.amount}"
        holder.address.text = "Address: ${order.address}"
        holder.username.text = "Ordered by: ${order.username}"
    }

    override fun getItemCount() = orderList.size

    fun updateData(newList: List<OrderNotification>) {
        orderList = newList
        notifyDataSetChanged()
    }
}
