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

        binding.saveBtn.setOnClickListener {
            saveStoreDetails()
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
                "contact" to contact
            )

            firestore.collection("users").document(userId)
                .update("store", storeData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Store details saved!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
        }
    }
}
