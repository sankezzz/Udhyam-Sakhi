package com.example.udyam.Buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.udyam.R
import com.example.udyam.adapters.OrderNotificationAdapter
import com.example.udyam.utils.OrderHistoryManager
import com.example.udyam.viewmodels.OrderViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BuyerNotifFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OrderNotificationAdapter
    private lateinit var fabRefresh: FloatingActionButton
    private val orderViewModel: OrderViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buyer_notif, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewOrders)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = OrderNotificationAdapter(emptyList())
        recyclerView.adapter = adapter

        fabRefresh = view.findViewById(R.id.fabRefresh)
        fabRefresh.setOnClickListener {
            orderViewModel.fetchLatestOrderOnce()
        }

        OrderHistoryManager.orderHistory.observe(viewLifecycleOwner, Observer { history ->
            adapter.updateData(history)
        })

        return view
    }
}
