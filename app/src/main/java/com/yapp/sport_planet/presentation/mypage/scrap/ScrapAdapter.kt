package com.yapp.sport_planet.presentation.mypage.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.mypage.MyBookMarksModel
import com.yapp.sport_planet.databinding.ItemScrapBinding

class ScrapAdapter(
    private val onClickDeleteAction: (Long, Boolean) -> Unit,
    private val onClickGoBoardAction: (Long) -> Unit
) :
    RecyclerView.Adapter<ScrapAdapter.ScrapViewHolder>() {

    val scrapItemList = mutableListOf<MyBookMarksModel>()

    fun setScrapItemList(item: List<MyBookMarksModel>) {
        scrapItemList.clear()
        scrapItemList.addAll(item)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapViewHolder {
        val binding = DataBindingUtil.inflate<ItemScrapBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_scrap,
            parent,
            false
        )
        return ScrapViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScrapViewHolder, position: Int) =
        holder.bind(scrapItemList[position])

    override fun getItemCount(): Int = scrapItemList.size

    inner class ScrapViewHolder(private val binding: ItemScrapBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.run {
                ivBookmark.setOnClickListener {
                    val boardId = scrapItemList[adapterPosition].boardId
                    if (scrapItemList.size == 1)
                        onClickDeleteAction(boardId, false)
                    else
                        onClickDeleteAction(boardId, true)
                }
                root.setOnClickListener {
                    val boardId = scrapItemList[adapterPosition].boardId
                    onClickGoBoardAction(boardId)
                }
            }

        }

        fun bind(item: MyBookMarksModel) {
            binding.run {
                items = item
                tvRecruitNumber.text = root.resources.getString(
                    R.string.item_history_ing_recruit_number,
                    item.recruitedNumber,
                    item.recruitNumber
                )
                when (item.groupStatus) {
                    "활동 종료", "모집 종료" -> {
                        setFinishedColorChange()
                    }

                }
            }
        }

        private fun ItemScrapBinding.setFinishedColorChange() {
            binding.root.resources.run {
                clItem.setBackgroundColor(getColor(R.color.white_gray, null))
                tvHostGuest.setTextColor(getColor(R.color.gray, null))
                tvDDay.setTextColor(getColor(R.color.gray, null))
                tvTitle.setTextColor(getColor(R.color.gray, null))
                tvGroupStatus.setBackgroundResource(R.drawable.shape_round_corner_gray)
                tvExercise.setBackgroundResource(R.drawable.shape_round_corner_light_gray)
                tvCity.setBackgroundResource(R.drawable.shape_round_corner_light_gray)
                tvRecruitNumber.setBackgroundResource(R.drawable.shape_round_corner_light_gray)
            }

        }
    }
}