package com.yapp.sport_planet.presentation.mypage.history.adapter

import android.os.Handler
import android.os.Looper
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.mypage.ApplyListModel
import com.yapp.sport_planet.data.model.mypage.MyViewHistoryModel
import com.yapp.sport_planet.databinding.ItemHistoryIngBinding

class IngTabAdapter(
    private val onClickListener: (MyViewHistoryModel) -> Unit,
    private val expandOnClickListener: (ApplyListModel) -> Unit
) :
    RecyclerView.Adapter<IngTabAdapter.IngTabViewHolder>() {

    val historyItem = mutableListOf<MyViewHistoryModel>()
    val applyListItem = mutableListOf<ApplyListModel>()
    private var selectedItems: SparseBooleanArray = SparseBooleanArray()


    fun setMyViewHistoryItem(item: List<MyViewHistoryModel>) {
        historyItem.clear()
        historyItem.addAll(item)
        notifyDataSetChanged()
    }

    fun setApplyListItem(item: List<ApplyListModel>) {
        applyListItem.clear()
        applyListItem.addAll(item)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
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
            val ingTabExpandAdapter: IngTabExpandAdapter by lazy {
                IngTabExpandAdapter(expandOnClickListener).apply {
                    setHasStableIds(true)
                }
            }
            binding.run {
                rvExpand.adapter = ingTabExpandAdapter
                root.setOnClickListener {
                    val item = historyItem[adapterPosition]
                    if (historyItem[adapterPosition].isHost) {
                        if (selectedItems.get(adapterPosition)) {
                            deleteSelectedItems()
                        } else {
                            onClickListener(item)
                            Handler(Looper.getMainLooper()).postDelayed({
                                run {
                                    putSelectItems(ingTabExpandAdapter)
                                }
                            }, 100)
                        }
                    }
                }
            }


        }

        private fun putSelectItems(ingTabExpandAdapter: IngTabExpandAdapter) {
            ingTabExpandAdapter.setApplyListItem(applyListItem)
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

        private fun deleteSelectedItems() {
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
        }

        fun bind(historyItem: MyViewHistoryModel) {
            binding.run {
                items = historyItem
                tvRecruitNumber.text = root.resources.getString(
                    R.string.item_history_ing_recruit_number,
                    historyItem.boardInfo.recruitedNumber,
                    historyItem.boardInfo.recruitNumber
                )
            }


        }
    }
}