package com.example.sport_planet.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import kotlinx.android.synthetic.main.item_board.view.*

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_board, parent, false)
    ) {
        private val profileImage = itemView.profile_image
        private val bookmark = itemView.bookmark
        private val nickname = itemView.nickname
        private val dayCount = itemView.dayCount
        private val title = itemView.title
        fun onBind(item: String) {
            title.text = item
        }
    }

    private val items: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}