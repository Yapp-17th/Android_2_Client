package com.example.sport_planet.presentation.custom.adpater

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sport_planet.R
import com.example.sport_planet.model.MenuEnum
import kotlinx.android.synthetic.main.item_custom_toolbar_menu.view.*

class CustomToolbarItemRecyclerAdapter :
    RecyclerView.Adapter<CustomToolbarItemRecyclerAdapter.ViewHolder>() {
    private val items: ArrayList<MenuEnum> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.onBind(items[position])

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<MenuEnum>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_custom_toolbar_menu, parent, false)
    ) {
        fun onBind(item: MenuEnum) {
            Glide.with(itemView).load(item.resourceId).into(itemView.icon)
        }
    }
}