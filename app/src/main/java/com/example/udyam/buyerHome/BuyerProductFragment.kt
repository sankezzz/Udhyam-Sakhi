package com.example.udyam.Buyer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.udyam.R
import com.google.firebase.firestore.FirebaseFirestore

class BuyerProductFragment : Fragment() {

    private lateinit var storeRecyclerView: RecyclerView
    private lateinit var storeAdapter: StoreAdapter
    private lateinit var storeList: ArrayList<StoreModel>
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buyer_product, container, false)

        storeRecyclerView = view.findViewById(R.id.rvStores)
        storeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        storeRecyclerView.setHasFixedSize(true)

        storeList = ArrayList()
        storeAdapter = StoreAdapter(storeList)
        storeRecyclerView.adapter = storeAdapter

        fetchStoresFromFirestore()

        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchStoresFromFirestore() {
        firestore.collection("stores")
            .get()
            .addOnSuccessListener { result ->
                storeList.clear()
                for (document in result) {
                    val storeName = document.getString("storeName") ?: "Untitled"
                    val address = document.getString("address") ?: "Unknown location"
                    val imageUrl = document.getString("imageUrl") ?: "https://via.placeholder.com/150"
                    val uid = document.id  // document ID used as UID

                    val store = StoreModel(
                        storeName = storeName,
                        location = address,
                        imageResId = imageUrl,
                        uid = uid
                    )
                    storeList.add(store)
                }
                storeAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Error fetching stores: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
