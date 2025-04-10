package com.example.udyam.Seller

import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.NavHostFragment
import com.example.udyam.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class SellerHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout before setContentView
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_home)

        // Make status and nav bars transparent
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        // Set light status/nav bar icons based on background
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true   // Set false if your background is dark
            isAppearanceLightNavigationBars = true
        }

        // Apply system bar insets padding to the root layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set up NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val titleTextView = findViewById<TextView>(R.id.text_title)

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
