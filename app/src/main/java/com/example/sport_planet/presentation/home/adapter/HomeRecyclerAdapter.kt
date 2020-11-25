package com.example.sport_planet.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ItemBoardBinding
import com.example.sport_planet.data.model.BoardModel

class HomeRecyclerAdapter : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
    private val items: ArrayList<BoardModel> = arrayListOf()

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

    fun setItems(items: List<BoardModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBoardBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(item: BoardModel) {
            if (item.groupStatus.code != 0) {
                binding.background.setBackgroundColor(itemView.context.getColor(R.color.white))
            } else {
                binding.background.setBackgroundColor(itemView.context.getColor(R.color.white_gray))
            }
            binding.tvTitle.text = item.title
            binding.ivBookmark.setImageResource(if (item.isBookMark) R.drawable.ic_star_enabled else R.drawable.ic_star_disabled)
            binding.tvNickname.text = item.hostName
        }
    }
}