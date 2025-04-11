package com.example.udyam.Buyer

data class StoreModel(
    val storeName: String = "",
    val location: String = "",
    val imageResId: String = "", // Make sure this is a URL if you're using Glide
    val uid: String = ""         // seller UID
)
