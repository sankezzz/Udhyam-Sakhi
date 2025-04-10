package com.example.udyam.Seller.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.udyam.R
import com.example.udyam.Seller.AddThreadActivity
import com.example.udyam.databinding.FragmentSellerCommunityBinding
import com.example.udyam.databinding.FragmentSellerProductsBinding


class SellerCommunityFragment : Fragment() {

    private lateinit var binding :FragmentSellerCommunityBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater , R.layout.fragment_seller_community, container , false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.fabAdd.setOnClickListener {
        val i = Intent(activity , AddThreadActivity::class.java)
            startActivity(i)
        }


    }

    }
