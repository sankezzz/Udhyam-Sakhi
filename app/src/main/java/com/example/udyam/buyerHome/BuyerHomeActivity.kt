package com.example.udyam.buyerHome

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buyer_home)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Transparent system bars
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_home) as NavHostFragment
        val navController = navHostFragment.navController

        // Notifications Button
        findViewById<ImageView>(R.id.btn_notification).setOnClickListener {
            showToast("Notifications")
            navController.navigate(R.id.buyerNotifFragment)
        }

        // Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView_buyer)
        val titleTextView = findViewById<TextView>(R.id.text_title)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home_buyer -> {
                    navigateWithToast("Home") { navController.navigate(R.id.buyerHomeFragment2) }
                    true
                }
                R.id.nav_products_buyer -> {
                    navigateWithToast("Products") { navController.navigate(R.id.buyerProductFragment) }
                    true
                }
                R.id.nav_cart -> {
                    navigateWithToast("Cart") {
                        val intent = Intent(this, CartActivity::class.java)
                        startActivity(intent)
                    }
                    true
                }
                else -> false
            }
        }

        // Floating Action Button for WhatsApp
        findViewById<ImageView>(R.id.fab_action_whatsapp).setOnClickListener {
            val phoneNumber = "+15556406473"
            val message = "hi"
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=${Uri.encode(message)}"
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                    setPackage("com.whatsapp")
                }
                startActivity(intent)
            } catch (e: Exception) {
                showToast("WhatsApp not installed!")
            }
        }

        // Drawer Setup
        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        findViewById<ImageView>(R.id.btn_side_nav).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_profile -> {
                    navigateWithToast("Drawer: Profile Clicked") {
                        navController.navigate(R.id.buyerProfileFragment2)
                    }
                }
                R.id.nav_orders -> {
                    navigateWithToast("Drawer: Orders Clicked") {
                        navController.navigate(R.id.buyerNotifFragment)
                    }
                }
                R.id.nav_logout -> {
                    auth.signOut()
                    val intent = Intent(this, AuthActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    startActivity(intent)
                    finish()
                    showToast("Drawer: Logout Clicked")
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private inline fun navigateWithToast(message: String, action: () -> Unit) {
        showToast(message)
        action()
    }
}
