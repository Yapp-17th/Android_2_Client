package com.example.sport_planet.presentation.mypage.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.data.model.MyViewHistoryModel
import com.example.sport_planet.databinding.ItemHistoryIngBinding

class IngTabAdapter(private val onClickListener: (MyViewHistoryModel) -> Unit) :
    RecyclerView.Adapter<IngTabAdapter.IngTabViewHolder>() {

    val historyItem = mutableListOf<MyViewHistoryModel>()

    fun setItem(item : List<MyViewHistoryModel>){
        historyItem.clear()
        historyItem.addAll(item)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngTabViewHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryIngBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history_ing,
            parent,
            false
        )
        return IngTabViewHolder(binding)
    }

    override fun getItemCount(): Int = historyItem.size

    override fun onBindViewHolder(holder: IngTabViewHolder, position: Int) {
        holder.bind(historyItem[position])
    }

    inner class IngTabViewHolder(private val binding: ItemHistoryIngBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val item = historyItem[adapterPosition]
                onClickListener(item)
            }
        }

        fun bind(historyItem: MyViewHistoryModel) {
            binding.items = historyItem
        }
    }
}