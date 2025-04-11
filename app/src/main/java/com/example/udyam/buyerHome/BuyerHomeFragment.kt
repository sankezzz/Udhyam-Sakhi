package com.example.udyam.Buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.udyam.R
import com.example.udyam.adapters.ProductAdapter
import com.example.udyam.models.Product

class BuyerHomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buyer_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val trendingRecycler = view.findViewById<RecyclerView>(R.id.rvTrendingProducts)
        val exploreRecycler = view.findViewById<RecyclerView>(R.id.rvExploreProducts)

        val sampleStores = listOf(
            StoreModel("Aarti's Decor", "Jalandhar", "https://via.placeholder.com/150", "uid1"),
            StoreModel("Pottery Wonders", "Jalandhar", "https://via.placeholder.com/150", "uid2"),
            StoreModel("Shawl Boutique", "Jalandhar", "https://via.placeholder.com/150", "uid3"),
            StoreModel("Eco Home", "Jalandhar", "https://via.placeholder.com/150", "uid4"),
            StoreModel("Crafts by Meera", "Jalandhar", "https://via.placeholder.com/150", "uid1"),
            StoreModel("Pottery Wonders", "Jaipur", "https://via.placeholder.com/150", "uid2"),
            StoreModel("Shawl Boutique", "Kashmir", "https://via.placeholder.com/150", "uid3"),
            StoreModel("Eco Home", "Shillong", "https://via.placeholder.com/150", "uid4"),
            StoreModel("Crafts by Meera", "Delhi", "https://via.placeholder.com/150", "uid1"),
            StoreModel("Pottery Wonders", "Jaipur", "https://via.placeholder.com/150", "uid2"),
            StoreModel("Shawl Boutique", "Kashmir", "https://via.placeholder.com/150", "uid3"),
            StoreModel("Eco Home", "Shillong", "https://via.placeholder.com/150", "uid4")
        )

        val storeClickListener: (StoreModel) -> Unit = { store ->
            Toast.makeText(requireContext(), "Clicked: ${store.storeName}", Toast.LENGTH_SHORT).show()
            // Navigate to store details if needed
        }

        trendingRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        trendingRecycler.adapter = StoreAdapter(sampleStores)

        exploreRecycler.layoutManager = GridLayoutManager(requireContext(), 1)
        exploreRecycler.adapter = StoreAdapter(sampleStores)

        trendingRecycler.setHasFixedSize(true)
        trendingRecycler.isNestedScrollingEnabled = false



    }


}
