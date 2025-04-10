package com.example.udyam.Seller

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.udyam.databinding.ActivityAddProductBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private val PICK_IMAGE_REQUEST = 1
    private var selectedImageUri: Uri? = null
    private val apiKey = "a55a29c04fe50e932764f6e4a1ad0361" // Replace with your actual ImgBB API key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.imgCard.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
        }

        binding.btnSubmit.setOnClickListener {
            if (selectedImageUri != null) {
                uploadImageToImgBB(selectedImageUri!!)
            } else {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data?.data != null) {
            selectedImageUri = data.data
            Glide.with(this).load(selectedImageUri).into(binding.threadImg)
        }
    }

    private fun uploadImageToImgBB(imageUri: Uri) {
        val inputStream = contentResolver.openInputStream(imageUri)
        val imageBytes = inputStream!!.readBytes()
        val base64Image = Base64.encodeToString(imageBytes, Base64.DEFAULT)

        val client = OkHttpClient()
        val requestBody = FormBody.Builder()
            .add("key", apiKey)
            .add("image", base64Image)
            .build()

        val request = Request.Builder()
            .url("https://api.imgbb.com/1/upload")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@AddProductActivity, "Image upload failed: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    Log.e("ImgBB Upload", "Error: ", e)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                Log.d("ImgBB Upload", "Response: $responseBody")
                val json = JSONObject(responseBody ?: "")
                if (json.has("data")) {
                    val link = json.getJSONObject("data").getString("url")
                    runOnUiThread {
                        Toast.makeText(this@AddProductActivity, "Image uploaded!", Toast.LENGTH_SHORT).show()
                    }
                    saveProductToFirestore(link)
                } else {
                    runOnUiThread {
                        Toast.makeText(this@AddProductActivity, "Upload failed", Toast.LENGTH_LONG).show()
                        Log.e("ImgBB Upload", "Unexpected response: $json")
                    }
                }
            }
        })
    }

    private fun saveProductToFirestore(imageUrl: String) {
        val name = binding.etItemname.text.toString().trim()
        val price = binding.etRate.text.toString().trim()
        val quantity = binding.etQuantity.text.toString().trim()
        val desc = binding.etDescription.text.toString().trim()

        if (name.isBlank() || price.isBlank() || quantity.isBlank() || desc.isBlank()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val product = hashMapOf(
            "name" to name,
            "price" to price,
            "quantity" to quantity,
            "description" to desc,
            "imageUrl" to imageUrl
        )

        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            Log.e("Firestore", "User ID is null")
            return
        }

        db.collection("products")
            .add(product)
            .addOnSuccessListener { docRef ->
                Log.d("Firestore", "Product added with ID: ${docRef.id}")
                db.collection("users").document(userId).collection("myProducts")
                    .document(docRef.id)
                    .set(product)
                    .addOnSuccessListener {
                        runOnUiThread {
                            Toast.makeText(this, "Product added successfully!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                    .addOnFailureListener { e ->
                        runOnUiThread {
                            Toast.makeText(this, "Added to 'products' but failed in 'myProducts': ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                            Log.e("Firestore", "myProducts error: ", e)
                        }
                    }
            }
            .addOnFailureListener { e ->
                runOnUiThread {
                    Toast.makeText(this, "Failed to add product: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                    Log.e("Firestore", "Error adding product", e)
                }
            }
    }
}
//