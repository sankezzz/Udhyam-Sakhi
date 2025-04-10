package com.example.udyam.Buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        // Sample product list
        val sampleProducts = listOf(
            Product("Handmade Basket", "250", "Woven with love", "https://via.placeholder.com/150"),
            Product("Clay Pot", "180", "Organic Clay pot", "https://via.placeholder.com/150"),
            Product("Wool Shawl", "550", "Perfect for winter", "https://via.placeholder.com/150"),
            Product("Bamboo Lamp", "460", "Eco-friendly decor", "https://via.placeholder.com/150")
        )

        // Setup Trending RecyclerView (Horizontal)
        trendingRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        trendingRecycler.adapter = ProductAdapter(sampleProducts)

        // Setup Explore RecyclerView (Grid)
        exploreRecycler.layoutManager = GridLayoutManager(requireContext(), 2)
        exploreRecycler.adapter = ProductAdapter(sampleProducts)
    }
}
