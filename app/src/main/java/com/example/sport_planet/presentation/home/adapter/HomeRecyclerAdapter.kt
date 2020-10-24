package com.example.sport_planet.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ItemBoardBinding

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    private val items: MutableList<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_board,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBoardBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(item: String) {
            binding.tvTitle.text = item
        }
    }
}