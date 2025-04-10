package com.example.udyam.Seller

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.udyam.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SellerHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_home)

        // Edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val titleTextView = findViewById<TextView>(R.id.text_title)

        // Optional: FloatingActionButton click
        val chatbotFAB = findViewById<FloatingActionButton>(R.id.chatbotFAB)
        chatbotFAB.setOnClickListener {
            Toast.makeText(this, "Chatbot Coming Soon!", Toast.LENGTH_SHORT).show()
            // startActivity(Intent(this, SellerChatbotActivity::class.java))
        }



        // Manual navigation + toast
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.sellerHomeFragment)
                    true
                }

                R.id.nav_community -> {
                    Toast.makeText(this, "Community", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.sellerCommunityFragment)
                    true
                }

                R.id.nav_products -> {
                    Toast.makeText(this, "Products", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.sellerProductsFragment)
                    true
                }

                else -> false
            }
        }
    }
}
