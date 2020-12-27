package com.yapp.sport_planet.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ItemSearchBinding

class SearchRecyclerAdapter(
    private val itemClick: (String) -> Unit,
    private val xClick: (Int) -> Unit
) : RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {
    private val items: ArrayList<String> = ArrayList()

    fun setItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: String) {
            binding.tvSearchContent.text = item
            binding.root.setOnClickListener {
                itemClick(items[adapterPosition])
            }
            binding.ivDelete.setOnClickListener {
                xClick(adapterPosition)
            }
            binding.executePendingBindings()
        }
    }
}