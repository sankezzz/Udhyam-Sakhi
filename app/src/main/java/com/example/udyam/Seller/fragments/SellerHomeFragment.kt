package com.example.udyam.Seller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.udyam.R
import com.example.udyam.adapters.InspirationAdapter
import com.example.udyam.databinding.FragmentSellerHomeBinding
import com.example.udyam.models.InspirationStory


class SellerHomeFragment : Fragment() {

    private var _binding: FragmentSellerHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var inspirationAdapter: InspirationAdapter
    private lateinit var inspirationList: List<InspirationStory>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Dummy data
        inspirationList = listOf(
            InspirationStory("Gazal Alagh", "Founder of Mama Earth", R.drawable.gazalalagh),
            InspirationStory("Falguni Nayar", "Founder of Nykaa", R.drawable.falguni),
            InspirationStory("Upasana Taku", "Co-founder of MobiKwik", R.drawable.upasana),
            InspirationStory("Vineeta Singh", "CEO of SUGAR Cosmetics", R.drawable.vineeta)
        )

        // Set up RecyclerView
        inspirationAdapter = InspirationAdapter(inspirationList)
        binding.recyclerInspirations.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = inspirationAdapter
        }

        // Update cards (you may fetch from backend or use LiveData in real implementation)
        binding.textTotalSalesValue.text = "₹12,500"
        binding.textOrdersReceivedValue.text = "30"
        binding.textOrdersDeliveredValue.text = "27"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
