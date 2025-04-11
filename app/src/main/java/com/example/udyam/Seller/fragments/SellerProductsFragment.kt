package com.example.udyam.Seller.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.udyam.R
import com.example.udyam.Seller.AddProductActivity
import com.example.udyam.adapters.ProductAdapter
import com.example.udyam.databinding.FragmentSellerProductsBinding
import com.example.udyam.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SellerProductsFragment : Fragment() {

    private lateinit var binding: FragmentSellerProductsBinding
    private lateinit var adapter: ProductAdapter
    private val productList = ArrayList<Product>()

    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seller_products, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Pass a click listener, even if just a simple toast or placeholder
        adapter = ProductAdapter(productList) { clickedProduct ->
            Toast.makeText(requireContext(), "Clicked: ${clickedProduct.name}", Toast.LENGTH_SHORT).show()
            // Optional: Navigate to a detail/edit screen here if needed
        }

        binding.productsRv.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.productsRv.adapter = adapter

        fetchMyProducts()

        binding.addProductFab.setOnClickListener {
            val intent = Intent(activity, AddProductActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun fetchMyProducts() {
        val currentUser = auth.currentUser ?: return
        db.collection("users")
            .document(currentUser.uid)
            .collection("myProducts")
            .get()
            .addOnSuccessListener { querySnapshot ->
                productList.clear()
                for (doc in querySnapshot.documents) {
                    val product = doc.toObject(Product::class.java)
                    product?.let { productList.add(it) }
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(requireContext(), "Failed to load products", Toast.LENGTH_SHORT).show()
            }
    }
}
