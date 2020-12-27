package com.yapp.sport_planet.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ItemExerciseRegionBinding

class RegionAdapter(private val onClickAction: (String, Long) -> Unit) :
    RecyclerView.Adapter<RegionAdapter.RegionViewHolder>() {
    private val items = mutableListOf<String>()

    fun setItem(item: List<String>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding = DataBindingUtil.inflate<ItemExerciseRegionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_exercise_region,
            parent,
            false
        )
        return RegionViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class RegionViewHolder(private val binding: ItemExerciseRegionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val item = items[adapterPosition]
                onClickAction(item, adapterPosition + 1L)
            }
        }

        fun bind(item: String) {
            binding.item = item
        }
    }
}
