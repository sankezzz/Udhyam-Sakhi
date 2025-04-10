package com.example.udyam.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.udyam.R
import com.example.udyam.models.InspirationStory

class InspirationAdapter(
    private val items: List<InspirationStory>
) : RecyclerView.Adapter<InspirationAdapter.InspirationViewHolder>() {

    inner class InspirationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textName: TextView = itemView.findViewById(R.id.text_name)
        val textDescription: TextView = itemView.findViewById(R.id.text_description)
        val imagePerson: ImageView = itemView.findViewById(R.id.image_person)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InspirationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inspiration, parent, false)
        return InspirationViewHolder(view)
    }

    override fun onBindViewHolder(holder: InspirationViewHolder, position: Int) {
        val item = items[position]
        holder.textName.text = item.name
        holder.textDescription.text = item.description
        holder.imagePerson.setImageResource(item.imageResId)
    }

    override fun getItemCount() = items.size
}
