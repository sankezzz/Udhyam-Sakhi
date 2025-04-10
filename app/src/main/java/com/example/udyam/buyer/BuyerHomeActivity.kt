package com.example.udyam.buyer

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.example.udyam.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class BuyerHomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_home)

        // Handle system bars for edge-to-edge UI
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigation Controller setup
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController


        val btnNotification = findViewById<ImageView>(R.id.btn_notification)

        btnNotification.setOnClickListener {
            Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
            navController.navigate(R.id.buyerNotifFragment)
        }

        // Bottom Navigation setup
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView_buyer)
        val titleTextView = findViewById<TextView>(R.id.text_title)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home_buyer -> {
                    Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.buyerHomeFragment2)
                    true
                }

                R.id.nav_products_buyer -> {
                    Toast.makeText(this, "Products", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.buyerProductFragment)
                    true
                }

                R.id.nav_profile -> {
                    Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.buyerProfileFragment2)
                    true
                }

                else -> false
            }
        }

        // DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val btnSideNav = findViewById<ImageView>(R.id.btn_side_nav)

        // Open drawer on clicking the side nav icon
        btnSideNav.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // Handle drawer item clicks
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    Toast.makeText(this, "Drawer: Profile Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_orders -> {
                    Toast.makeText(this, "Drawer: Orders Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    Toast.makeText(this, "Drawer: Logout Clicked", Toast.LENGTH_SHORT).show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}
