package com.yapp.sport_planet.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.BoardModel
import com.yapp.sport_planet.databinding.ItemBoardBinding

class HomeRecyclerAdapter(
    private val itemClick: (Long) -> Unit,
    private val bookMarkClick: (BoardModel) -> Unit
) : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {
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
        this.items.addAll(
            items.asSequence()
                .filter { it.groupStatus.code != 3 }
                .sortedBy { it.groupStatus.code }
        )
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBoardBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun onBind(item: BoardModel) {
            binding.root.setOnClickListener { itemClick(item.boardId) }
            if (item.groupStatus.code == 0) {
                binding.background.setBackgroundColor(itemView.context.getColor(R.color.white))
                binding.tvHost.setTextColor(itemView.context.getColor(R.color.pink))
                binding.tvNickname.setTextColor(itemView.context.getColor(R.color.black))
                binding.tvDayCount.setTextColor(itemView.context.getColor(R.color.black))
                binding.tvTitle.setTextColor(itemView.context.getColor(R.color.black))
                binding.tvStatus.setBackgroundResource(R.drawable.shape_round_corner_darkblue)
                binding.tvExercise.setBackgroundResource(R.drawable.shape_round_corner_darkblue_opacity_no_stoke)
                binding.tvRegion.setBackgroundResource(R.drawable.shape_round_corner_darkblue_opacity_no_stoke)
                binding.tvPeopleCount.setBackgroundResource(R.drawable.shape_round_corner_darkblue_opacity_no_stoke)
            } else {
                binding.background.setBackgroundColor(itemView.context.getColor(R.color.white_gray))
                binding.tvHost.setTextColor(itemView.context.getColor(R.color.gray9c9c9c))
                binding.tvNickname.setTextColor(itemView.context.getColor(R.color.dark_gray))
                binding.tvDayCount.setTextColor(itemView.context.getColor(R.color.gray9c9c9c))
                binding.tvTitle.setTextColor(itemView.context.getColor(R.color.gray9c9c9c))
                binding.tvStatus.setBackgroundResource(R.drawable.shape_round_corner_gray)
                binding.tvExercise.setBackgroundResource(R.drawable.shape_round_corner_light_gray)
                binding.tvRegion.setBackgroundResource(R.drawable.shape_round_corner_light_gray)
                binding.tvPeopleCount.setBackgroundResource(R.drawable.shape_round_corner_light_gray)
            }
            binding.tvTitle.text = item.title
            binding.ivBookmark.setImageResource(if (item.isBookMark) R.drawable.ic_star_enabled else R.drawable.ic_star_disabled)
            binding.ivBookmark.setOnClickListener {
                bookMarkClick(item)
            }
            binding.tvDayCount.text = item.boardTime
            binding.tvNickname.text = item.hostName
            binding.tvStatus.text = item.groupStatus.name

            binding.tvExercise.text = item.exercise
            binding.tvRegion.text = item.city
            binding.tvPeopleCount.text =
                binding.root.context.getString(R.string.item_history_ing_recruit_number)
                    .format(item.recruitedNumber, item.recruitNumber)
        }
    }
}