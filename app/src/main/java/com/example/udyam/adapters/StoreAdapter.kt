package com.example.udyam.Buyer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.udyam.R

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
        holder.storeImage.setImageResource(store.imageResId)
    }

    override fun getItemCount(): Int = storeList.size
}
