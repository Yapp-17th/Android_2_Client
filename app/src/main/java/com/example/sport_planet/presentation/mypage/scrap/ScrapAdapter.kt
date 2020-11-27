package com.example.sport_planet.presentation.mypage.scrap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.data.model.MyBookMarksModel
import com.example.sport_planet.databinding.ItemScrapBinding

class ScrapAdapter(private val onClickDeleteAction: (Long,Boolean) -> Unit, private val onClickGoBoardAction :(Long) -> Unit) :
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
            val boardId =scrapItemList[adapterPosition].boardId
            binding.ivBookmark.setOnClickListener {
                if(scrapItemList.size == 1)
                    onClickDeleteAction(boardId,false)
                else
                    onClickDeleteAction(boardId,true)

                scrapItemList.removeAt(adapterPosition)
                notifyItemChanged(adapterPosition)
            }
            binding.root.setOnClickListener { 
                onClickGoBoardAction(boardId)
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
            }
        }
    }
}