package com.example.udyam.buyerHome

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.udyam.R
import com.example.udyam.adapters.ProductAdapter
import com.example.udyam.databinding.ActivityStoresProductBinding
import com.example.udyam.models.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StoresProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoresProductBinding
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var productAdapter: ProductAdapter
    private val productList = ArrayList<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoresProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Confirm user is authenticated
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            finish()
            return
        } else {
            Log.d("Auth", "Logged in as UID: ${currentUser.uid}")
        }

        // Get sellerUid from intent
        val sellerUid = intent.getStringExtra("sellerUid")
        if (sellerUid.isNullOrEmpty()) {
            Toast.makeText(this, "Seller UID not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        } else {
            Log.d("SellerUID", "Seller UID: $sellerUid")
        }

        // Set up RecyclerView
        productAdapter = ProductAdapter(productList) { selectedProduct ->
            addToCart(selectedProduct)
        }
        binding.proRv.layoutManager = GridLayoutManager(this, 2)
        binding.proRv.adapter = productAdapter

        // Fetch products
        fetchProducts(sellerUid)
    }

    private fun fetchProducts(sellerUid: String) {
        firestore.collection("users")
            .document(sellerUid)
            .collection("myProducts")
            .get()
            .addOnSuccessListener { result ->
                Log.d("Firestore", "Fetched ${result.size()} products")
                productList.clear()

                for (document in result) {
                    Log.d("Product", "Data: ${document.data}")

                    val name = document.getString("name") ?: "Unnamed"
                    val price = document.getString("price") ?: "0"
                    val imageUrl = document.getString("imageUrl") ?: ""

                    val product = Product(name, price, imageUrl)
                    productList.add(product)
                }

                productAdapter.notifyDataSetChanged()

                if (productList.isEmpty()) {
                    Toast.makeText(this, "No products found", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Log.e("FirestoreError", "Error fetching products: ${it.message}", it)
                Toast.makeText(this, "Failed to load products: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }


    private fun addToCart(product: Product) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
            return
        }

        val cartItem = hashMapOf(
            "name" to product.name,
            "price" to product.price,
            "imageUrl" to product.imageUrl,
            "timestamp" to System.currentTimeMillis()
        )

        FirebaseFirestore.getInstance()
            .collection("users")
            .document(currentUser.uid)
            .collection("cart")  // ✅ moved under user
            .add(cartItem)
            .addOnSuccessListener {
                Toast.makeText(this, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
                Log.d("Cart", "Added to cart: $cartItem")
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to add to cart: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("CartError", "Error adding to cart", e)
            }
    }


}
