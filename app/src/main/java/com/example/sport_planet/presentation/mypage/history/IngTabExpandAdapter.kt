package com.example.sport_planet.presentation.mypage.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.data.model.ApplyListModel
import com.example.sport_planet.databinding.ItemHistoryIngExpandBinding

class IngTabExpandAdapter : RecyclerView.Adapter<IngTabExpandAdapter.IngTabExpandViewHolder>() {

    private val applyListItem = mutableListOf<ApplyListModel>()
    fun setApplyListItem(item: List<ApplyListModel>) {
        applyListItem.clear()
        applyListItem.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngTabExpandViewHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryIngExpandBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history_ing_expand,
            parent,
            false
        )
        return IngTabExpandViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngTabExpandViewHolder, position: Int) {
        holder.bind(applyListItem[position])
    }

    override fun getItemCount(): Int = applyListItem.size

    inner class IngTabExpandViewHolder(private val binding: ItemHistoryIngExpandBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ApplyListModel) {
            binding.items = item
            when (item.applyStatus.code) {
                0 -> {
                    binding.tvLabel.setTextColor(
                        binding.root.resources.getColor(
                            R.color.dark_gray,
                            null
                        )
                    )
                    binding.tvLabel.setBackgroundResource(R.drawable.shape_round_corner_light_gray)
                }
                1, 2 -> {
                    binding.tvLabel.setTextColor(
                        binding.root.resources.getColor(
                            R.color.darkblue,
                            null
                        )
                    )
                    binding.tvLabel.setBackgroundResource(R.drawable.shape_round_corner_darkblue_opacity)
                }
            }
        }
    }
}