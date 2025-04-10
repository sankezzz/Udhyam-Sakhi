package com.example.udyam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.udyam.R
import com.example.udyam.models.OrderNotification

class OrderNotificationAdapter(
    private val orderList: List<OrderNotification>
) : RecyclerView.Adapter<OrderNotificationAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orderStatus: TextView = itemView.findViewById(R.id.tvOrderStatus)
        val storeName: TextView = itemView.findViewById(R.id.tvStoreName)
        val pricePaid: TextView = itemView.findViewById(R.id.tvPricePaid)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_notification, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.orderStatus.text = order.orderStatus
        holder.storeName.text = "Store: ${order.storeName}"
        holder.pricePaid.text = "Paid: ${order.pricePaid}"
    }

    override fun getItemCount(): Int = orderList.size
}
