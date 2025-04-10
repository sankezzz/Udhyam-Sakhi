package com.example.udyam.Buyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.udyam.R
import com.example.udyam.adapters.OrderNotificationAdapter
import com.example.udyam.models.OrderNotification


class BuyerNotifFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderNotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buyer_notif, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Sample data
        val orderList = listOf(
            OrderNotification("Order Placed", "ABC Mart", "₹499"),
            OrderNotification("Shipped", "Grocery Hub", "₹749"),
            OrderNotification("Delivered", "Daily Needs", "₹349")
        )

        adapter = OrderNotificationAdapter(orderList)
        recyclerView.adapter = adapter

        return view
    }
}
