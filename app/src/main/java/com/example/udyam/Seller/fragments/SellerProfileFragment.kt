package com.example.udyam.Seller.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.udyam.R
import com.example.udyam.auth.AuthActivity
import com.example.udyam.databinding.FragmentSellerProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SellerProfileFragment : Fragment() {

    private var _binding: FragmentSellerProfileBinding? = null
    private val binding get() = _binding!!

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private lateinit var signOutBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellerProfileBinding.inflate(inflater, container, false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchSellerData()
        signOutBtn = view.findViewById(R.id.btn_sign_out)

        signOutBtn.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun fetchSellerData() {
        val uid = auth.currentUser?.uid
        if (uid == null) {
            showToast("User not logged in.")
            return
        }

        firestore.collection("users").document(uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val name = document.getString("name") ?: "N/A"
                    val contact = document.getString("contact") ?: "N/A"
                    val address = document.getString("address") ?: "N/A"
                    val pincode = document.getString("pincode") ?: "N/A"
                    val email = document.getString("email") ?: "N/A"

                    binding.sellerNameTv.text = "Name: $name"
                    binding.sellerPhoneTv.text = "Phone: $contact"
                    binding.sellerShopAddressTv.text = "Shop Address: $address"
                    binding.sellerPincodeTv.text = "Pincode: $pincode"
                    binding.sellerEmailTv.text = "Email: $email"
                } else {
                    showToast("User data not found.")
                }
            }
            .addOnFailureListener {
                showToast("Error fetching data: ${it.message}")
            }
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
