package com.example.udyam.buyer

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.example.udyam.R
import com.example.udyam.auth.AuthActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BuyerHomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge layout before setContentView
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_home)

         val auth = FirebaseAuth.getInstance()
         val firestore = FirebaseFirestore.getInstance()

        // Make system bars transparent
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        // Set light/dark icons depending on background
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true  // Use false if your background is dark
            isAppearanceLightNavigationBars = true
        }

        // Apply padding for system bars (if needed)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
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

        val fab = findViewById<FloatingActionButton>(R.id.fab_action_whatsapp)
        fab.setOnClickListener {
            val phoneNumber = "+15556406473"
            val message = "hi"
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"
                intent.setPackage("com.whatsapp")
                intent.data = Uri.parse(url)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, "WhatsApp not installed!", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }

        // DrawerLayout and NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val btnSideNav = findViewById<ImageView>(R.id.btn_side_nav)

        btnSideNav.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    navController.navigate(R.id.buyerProfileFragment2)
                    Toast.makeText(this, "Drawer: Profile Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_orders -> {
                    Toast.makeText(this, "Drawer: Orders Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
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
    }
}
