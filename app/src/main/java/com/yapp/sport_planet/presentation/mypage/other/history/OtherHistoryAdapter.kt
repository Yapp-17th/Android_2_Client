package com.yapp.sport_planet.presentation.mypage.other.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.OtherHistoryModel
import com.yapp.sport_planet.databinding.ItemOtherHistoryBinding

class OtherHistoryAdapter :
    RecyclerView.Adapter<OtherHistoryAdapter.OtherHistoryViewHolder>() {

    private val historyItem = mutableListOf<OtherHistoryModel>()

    fun setOtherHistoryItem(item: List<OtherHistoryModel>) {
        historyItem.clear()
        historyItem.addAll(item)
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherHistoryViewHolder {
        val binding = DataBindingUtil.inflate<ItemOtherHistoryBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_other_history,
            parent,
            false
        )
        return OtherHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OtherHistoryViewHolder, position: Int) {
        holder.bind(historyItem[position])
    }

    override fun getItemCount(): Int = historyItem.size

    inner class OtherHistoryViewHolder(private val binding: ItemOtherHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OtherHistoryModel) {
            binding.run {
                items = item
                tvRecruitNumber.text = root.resources.getString(
                    R.string.item_history_ing_recruit_number,
                    item.boardInfo.recruitedNumber,
                    item.boardInfo.recruitNumber
                )
            }
        }
    }
}