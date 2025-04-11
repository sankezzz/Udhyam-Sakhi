package com.example.udyam.Buyer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.udyam.R
import com.example.udyam.buyerHome.StoresProductActivity

class StoreAdapter(private val storeList: List<StoreModel>) :
    RecyclerView.Adapter<StoreAdapter.StoreViewHolder>() {

    class StoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storeImage: ImageView = itemView.findViewById(R.id.imgStore)
        val storeName: TextView = itemView.findViewById(R.id.tvStoreName)
        val storeLocation: TextView = itemView.findViewById(R.id.tvStoreLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_store_card, parent, false)
        return StoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store = storeList[position]
        holder.storeName.text = store.storeName
        holder.storeLocation.text = store.location

        Glide.with(holder.itemView.context)
            .load(store.imageResId)
            .placeholder(R.drawable.handmade1) // 👈 your placeholder image here
            .into(holder.storeImage)


        // On click, open StoresProductActivity and pass the UID
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, StoresProductActivity::class.java)
            intent.putExtra("sellerUid", store.uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = storeList.size
}
