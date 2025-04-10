package com.example.udyam.Seller.fragments
import UThread
import UThreadAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.udyam.R
import com.example.udyam.Seller.AddThreadActivity
import com.example.udyam.databinding.FragmentSellerCommunityBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class SellerCommunityFragment : Fragment() {

    private lateinit var binding: FragmentSellerCommunityBinding
    private lateinit var adapter: UThreadAdapter
    private val threadList = mutableListOf<UThread>()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seller_community, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup RecyclerView
        adapter = UThreadAdapter(threadList)
        binding.updatesRv.layoutManager = LinearLayoutManager(requireContext())
        binding.updatesRv.adapter = adapter

        // FAB to add new thread
        binding.fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddThreadActivity::class.java)
            startActivity(intent)
        }

        // Fetch threads
        fetchThreads()
    }



    private fun fetchThreads() {
        db.collection("threads")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) return@addSnapshotListener

                threadList.clear()
                snapshot?.forEach { doc ->
                    val thread = doc.toObject(UThread::class.java)

                    // Safely read timestamp as Long
                    val timestampLong = doc.getLong("timestamp") // <-- handles Firestore Long

                    val formattedTime = timestampLong?.let {
                        val date = Date(it) // if it's in milliseconds
                        // If your value is in seconds, use: Date(it * 1000)
                        SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(date)
                    } ?: ""

//                    thread.timestamp = formattedTime
                    threadList.add(thread)
                }
                adapter.notifyDataSetChanged()
            }
    }


}
