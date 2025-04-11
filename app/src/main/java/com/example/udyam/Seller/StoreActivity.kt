package com.example.udyam.Seller

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.udyam.R
import com.example.udyam.databinding.ActivityStoreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_store)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // ✅ Fetch existing store data
        fetchExistingStoreData()

        binding.saveBtn.setOnClickListener {
            saveStoreDetails()
        }
    }

    private fun fetchExistingStoreData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.contains("store")) {
                        val store = document.get("store") as Map<*, *>

                        binding.storeNameEt.setText(store["storeName"] as? String ?: "")
                        binding.stLocationEt.setText(store["address"] as? String ?: "")
                        binding.pincodeEt.setText(store["pincode"] as? String ?: "")
                        binding.contact.setText(store["contact"] as? String ?: "")
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to load store info: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun saveStoreDetails() {
        val storeName = binding.storeNameEt.text.toString().trim()
        val address = binding.stLocationEt.text.toString().trim()
        val pincode = binding.pincodeEt.text.toString().trim()
        val contact = binding.contact.text.toString().trim()

        if (storeName.isEmpty() || address.isEmpty() || pincode.isEmpty() || contact.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = auth.currentUser?.uid
        if (userId != null) {
            val storeData = hashMapOf(
                "storeName" to storeName,
                "address" to address,
                "pincode" to pincode,
                "contact" to contact,
                "userId" to userId
            )

            val storeMap = hashMapOf(
                "store" to storeData
            )

            // Save in user's document
            firestore.collection("users").document(userId)
                .set(storeMap, com.google.firebase.firestore.SetOptions.merge())
                .addOnSuccessListener {
                    // Save in global stores collection
                    firestore.collection("stores").document(userId)
                        .set(storeData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Store details saved successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to save store globally: ${it.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error saving user store: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
