package com.example.udyam.Seller

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.example.udyam.R
import com.example.udyam.auth.AuthActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SellerHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout before setContentView
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_home)

        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()

        // Set up NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val titleTextView = findViewById<TextView>(R.id.text_title)

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
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val sideMenuButton = findViewById<ImageView>(R.id.btn_side_nav)

        sideMenuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile_seller -> {
                    Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                    // Optionally navigate to a profile fragment
                    navController.navigate(R.id.sellerProfileFragment)
                }
                R.id.nav_orders_seller -> {
                    Toast.makeText(this, "Orders clicked", Toast.LENGTH_SHORT).show()
                   navController.navigate(R.id.sellerProductsFragment)
                }
                R.id.nav_store_seller ->{
                    val intent = Intent(this, StoreActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                R.id.nav_logout_seller -> {
                    Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                    val intent = Intent(this, AuthActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Drawer: Logout Clicked", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }



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
