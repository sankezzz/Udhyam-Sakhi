package com.example.udyam.buyerHome

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.udyam.R
import com.example.udyam.adapters.ProductAdapter
import com.example.udyam.databinding.ActivityCartBinding
import com.example.udyam.models.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private val db by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val cartItems = mutableListOf<Product>()
    private var userOrderJson: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fetchCartItemsAndDisplay()

        binding.buyButton.setOnClickListener {
            if (userOrderJson.isNotEmpty()) {
                val jsonWithQuantity = convertOrderJsonWithQuantity(userOrderJson)
                Log.d("ConvertedJSONWithQty", jsonWithQuantity)
                postOrderToApi("[$jsonWithQuantity]")
            } else {
                Toast.makeText(this, "Cart is empty or not loaded yet", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchCartItemsAndDisplay() {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        db.collection("users")
            .document(uid)
            .collection("cart")
            .get()
            .addOnSuccessListener { result ->
                cartItems.clear()
                var totalAmount = 0
                val orderItems = mutableListOf<OrderItem>()

                for (doc in result.documents) {
                    val item = doc.toObject(Product::class.java)
                    item?.let {
                        cartItems.add(it)

                        val priceInt = when (val price = it.price) {
                            is String -> price.toIntOrNull() ?: 0

                            else -> 0
                        }

                        orderItems.add(OrderItem(name = it.name ?: "Unknown", price = priceInt))
                        totalAmount += priceInt
                    }
                }
                val productClickListener: (Product) -> Unit = { product ->
                Toast.makeText(this, "Clicked: ${product.name}", Toast.LENGTH_SHORT).show()
                // You can navigate to a product detail screen here if needed
            }

                binding.cartsRv.layoutManager = GridLayoutManager(this, 2)
                binding.cartsRv.adapter = ProductAdapter(cartItems , productClickListener)

                val userOrder = UserOrder(
                    username = "Aditya",
                    address = "Meerut, UP",
                    timestamp = getCurrentTimestamp(),
                    payment_id = "UPI999XYZ",
                    items = orderItems,
                    total = totalAmount
                )
                // Create a shared click listener for both adapters


                userOrderJson = Gson().toJson(userOrder)
                Log.d("FormattedCartJSON", userOrderJson)
            }
            .addOnFailureListener { exception ->
                Log.e("CartError", "Error fetching cart: ", exception)
                Toast.makeText(this, "Failed to load cart", Toast.LENGTH_SHORT).show()
            }
    }

    private fun postOrderToApi(jsonBody: String) {
        val client = OkHttpClient()
        val requestBody = jsonBody.toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url("https://4b10-14-139-61-211.ngrok-free.app/api/bulk")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CartActivity, "POST failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CartActivity, "Order posted successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@CartActivity, "Error: ${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun convertOrderJsonWithQuantity(originalJson: String): String {
        val gson = Gson()
        val originalOrder = gson.fromJson(originalJson, UserOrder::class.java)

        val itemMap = mutableMapOf<Pair<String, Int>, Int>()
        for (item in originalOrder.items) {
            val key = item.name to item.price
            itemMap[key] = itemMap.getOrDefault(key, 0) + 1
        }

        val finalItems = itemMap.map { (key, quantity) ->
            FinalOrderItem(name = key.first, price = key.second, quantity = quantity)
        }

        val finalOrder = FinalUserOrder(
            username = originalOrder.username,
            address = originalOrder.address,
            timestamp = originalOrder.timestamp,
            payment_id = originalOrder.payment_id,
            items = finalItems,
            total = originalOrder.total
        )

        return gson.toJson(finalOrder)
    }
}
