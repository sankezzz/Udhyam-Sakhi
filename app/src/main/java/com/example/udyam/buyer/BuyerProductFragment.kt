package com.example.udyam.Buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.udyam.R

class BuyerProductFragment : Fragment() {

    private lateinit var storeRecyclerView: RecyclerView
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var storeList: ArrayList<StoreModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buyer_product, container, false)

        storeRecyclerView = view.findViewById(R.id.rvStores)
        storeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        storeRecyclerView.setHasFixedSize(true)

        storeList = ArrayList()
        populateDummyStores()

        storeAdapter = StoreAdapter(storeList)
        storeRecyclerView.adapter = storeAdapter

        return view
    }

    private fun populateDummyStores() {
        storeList.add(StoreModel("Fashion Fiesta", "Delhi", R.drawable.handmade1))
        storeList.add(StoreModel("Gadget World", "Mumbai", R.drawable.handmade2))
        storeList.add(StoreModel("Fresh Mart", "Bangalore", R.drawable.handmade1))
        storeList.add(StoreModel("Decor Den", "Chandigarh", R.drawable.handmade2))
    }
}
