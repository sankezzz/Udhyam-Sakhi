package com.example.udyam.Seller.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.udyam.databinding.FragmentSellerHomeBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class SellerHomeFragment : Fragment() {

    private var _binding: FragmentSellerHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSellerHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update dummy card values
        binding.textTotalSalesValue.text = "₹12,500"
        binding.textOrdersPendingValue.text = "30"
        binding.textOrdersDeliveredValue.text = "27"

        // Set up the graph
        setupSalesGraph()
    }

    private fun setupSalesGraph() {
        // Sample data for each day of the week (Mon to Sun)
        val entries = listOf(
            Entry(0f, 12f),  // Monday
            Entry(1f, 18f),  // Tuesday
            Entry(2f, 7f),   // Wednesday
            Entry(3f, 14f),  // Thursday
            Entry(4f, 20f),  // Friday
            Entry(5f, 10f),  // Saturday
            Entry(6f, 16f)   // Sunday
        )

        val lineDataSet = LineDataSet(entries, "Orders This Week").apply {
            color = Color.parseColor("#3461FD")
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 5f
            setCircleColor(Color.parseColor("#3461FD"))
            mode = LineDataSet.Mode.CUBIC_BEZIER
            setDrawValues(true)
        }

        val lineData = LineData(lineDataSet)
        binding.chartSalesGraph.apply {
            data = lineData
            description.isEnabled = false
            setTouchEnabled(true)
            setPinchZoom(true)
            animateX(1000)

            // X Axis
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                granularity = 1f
                setDrawGridLines(false)
                valueFormatter = IndexAxisValueFormatter(
                    listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                )
            }

            // Y Axis
            axisRight.isEnabled = false
            axisLeft.granularity = 1f

            invalidate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
