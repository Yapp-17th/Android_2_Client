package com.yapp.sport_planet.presentation.mypage.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.mypage.ApplyListModel
import com.yapp.sport_planet.databinding.ItemHistoryIngExpandBinding

class IngTabExpandAdapter(private val onClickListener: (ApplyListModel) -> Unit) :
    RecyclerView.Adapter<IngTabExpandAdapter.IngTabExpandViewHolder>() {

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
        init {
            binding.root.setOnClickListener {
                onClickListener(applyListItem[adapterPosition])
            }
        }

        fun bind(item: ApplyListModel) {
            binding.run {
                items = item
                if (item.isHost) {
                    tvHostGuest.setTextColor(root.resources.getColor(R.color.pink, null))
                } else {
                    tvHostGuest.setTextColor(root.resources.getColor(R.color.skyblue, null))
                }
                when (item.applyStatus.code) {
                    0 -> {
                        setTextLabel(R.color.dark_gray, R.drawable.shape_round_corner_light_gray)
                    }
                    1, 2 -> {
                        setTextLabel(
                            R.color.dark_blue,
                            R.drawable.shape_round_corner_darkblue_opacity_no_stoke
                        )
                    }
                }
            }

        }

        private fun ItemHistoryIngExpandBinding.setTextLabel(
            @ColorRes color: Int,
            @DrawableRes drawable: Int
        ) {
            tvLabel.run {
                setTextColor(root.resources.getColor(color, null))
                setBackgroundResource(drawable)
            }

        }
    }
}