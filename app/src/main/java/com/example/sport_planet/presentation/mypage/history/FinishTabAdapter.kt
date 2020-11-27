package com.example.sport_planet.presentation.mypage.history

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.data.model.MyViewHistoryModel
import com.example.sport_planet.data.response.EvaluateListResponse
import com.example.sport_planet.databinding.ItemHistoryFinishBinding

class FinishTabAdapter(private val onClickListener: (MyViewHistoryModel) -> Unit,private val dialogListener : (Long) -> Unit) :
    RecyclerView.Adapter<FinishTabAdapter.FinishTabViewHolder>() {

    val historyItem = mutableListOf<MyViewHistoryModel>()
    val applyListItem = mutableListOf<EvaluateListResponse.EvaluateListModel>()
    private var selectedItems: SparseBooleanArray = SparseBooleanArray()

    fun setMyViewHistoryItem(item: List<MyViewHistoryModel>) {
        historyItem.clear()
        historyItem.addAll(item)
        notifyDataSetChanged()
    }

    fun setApplyListItem(item: List<EvaluateListResponse.EvaluateListModel>) {
        applyListItem.clear()
        applyListItem.addAll(item)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinishTabViewHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryFinishBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history_finish,
            parent,
            false
        )
        return FinishTabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FinishTabViewHolder, position: Int) {
        holder.bind(historyItem[position])
    }

    override fun getItemCount(): Int = historyItem.size

    inner class FinishTabViewHolder(private val binding: ItemHistoryFinishBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            val finishTabExpandAdapter: FinishTabExpandAdapter by lazy {
                FinishTabExpandAdapter(dialogListener).apply {
                    setHasStableIds(true)
                }
            }
            binding.rvExpand.adapter = finishTabExpandAdapter
            binding.root.setOnClickListener {
                val item = historyItem[adapterPosition]
                if (selectedItems.get(adapterPosition)) {
                    selectedItems.delete(adapterPosition)
                    binding.run {
                        rvExpand.visibility = View.GONE
                        tvExpand.text =
                            root.resources.getString(R.string.item_history_ing_expand)
                        tvExpand.setCompoundDrawablesRelativeWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.icons_18_px_toggle,
                            0
                        )
                        applyListItem.clear()
                    }
                } else {
                    onClickListener(item)
                    Handler(Looper.getMainLooper()).postDelayed({
                        run {
                            finishTabExpandAdapter.setApplyListItem(applyListItem)
                            finishTabExpandAdapter.boardIdItem =
                                historyItem[adapterPosition].boardInfo.boardId
                            selectedItems.put(adapterPosition, true)
                            binding.run {
                                rvExpand.visibility = View.VISIBLE
                                tvExpand.run {
                                    text =
                                        binding.root.resources.getString(R.string.item_history_ing_unexpand)
                                    setCompoundDrawablesRelativeWithIntrinsicBounds(
                                        0,
                                        0,
                                        R.drawable.icons_18_px_untoggle,
                                        0
                                    )
                                }
                            }
                        }
                    }, 500)
                }
            }
        }

        fun bind(item: MyViewHistoryModel) {
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