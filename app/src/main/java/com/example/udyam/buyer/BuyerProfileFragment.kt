package com.example.udyam.Buyer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.udyam.R
import com.example.udyam.auth.AuthActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BuyerProfileFragment : Fragment() {

    private lateinit var nameTv: TextView
    private lateinit var phoneTv: TextView
    private lateinit var addressTv: TextView
    private lateinit var pincodeTv: TextView
    private lateinit var emailTv: TextView

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var signOutBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buyer_profile, container, false)

        // Initialize views
        nameTv = view.findViewById(R.id.buyer_name_tv)
        phoneTv = view.findViewById(R.id.buyer_phone_tv)
        addressTv = view.findViewById(R.id.buyer_address_tv)
        pincodeTv = view.findViewById(R.id.buyer_pincode_tv)
        emailTv = view.findViewById(R.id.buyer_email_tv)
        signOutBtn = view.findViewById(R.id.btn_sign_out)

        fetchUserData()

        signOutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        return view
    }

    private fun fetchUserData() {
        val currentUser = auth.currentUser
        val uid = currentUser?.uid

        if (uid != null) {
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("name") ?: "N/A"
                        val phone = document.getString("contact") ?: "N/A"
                        val address = document.getString("address") ?: "N/A"
                        val pincode = document.getString("pincode") ?: "N/A"
                        val email = document.getString("email") ?: "N/A"

                        nameTv.text = "Name: $name"
                        phoneTv.text = "Phone: $phone"
                        addressTv.text = "Address: $address"
                        pincodeTv.text = "Pincode: $pincode"
                        emailTv.text = "Email: $email"
                    } else {
                        showToast("User data not found")
                    }
                }
                .addOnFailureListener {
                    showToast("Failed to fetch user data: ${it.message}")
                }
        } else {
            showToast("User not logged in")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
