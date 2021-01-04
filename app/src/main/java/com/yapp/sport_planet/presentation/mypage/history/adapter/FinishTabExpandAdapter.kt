package com.yapp.sport_planet.presentation.mypage.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.mypage.EvaluateListModel
import com.yapp.sport_planet.databinding.ItemHistoryFinishExpandBinding
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
import com.yapp.sport_planet.util.applySchedulers
import io.reactivex.disposables.CompositeDisposable

class FinishTabExpandAdapter(private val onClickAction: (Long) -> Unit) :
    RecyclerView.Adapter<FinishTabExpandAdapter.FinishTabExpandViewHolder>() {

    private val applyListItem = mutableListOf<EvaluateListModel>()
    var boardIdItem: Long = 0L
    private val compositeDisposable = CompositeDisposable()

    fun setApplyListItem(item: List<EvaluateListModel>) {
        applyListItem.clear()
        applyListItem.addAll(item)
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FinishTabExpandViewHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryFinishExpandBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history_finish_expand, parent, false
        )
        return FinishTabExpandViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FinishTabExpandViewHolder,
        position: Int
    ) {
        holder.bind(applyListItem[position])
    }

    override fun getItemCount(): Int = applyListItem.size

    inner class FinishTabExpandViewHolder(private val binding: ItemHistoryFinishExpandBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EvaluateListModel) {
            binding.run {
                items = item
                ivLikes.setOnClickListener {
                    getLikeEvaluateList(item)
                }
                tvReport.setOnClickListener {
                    onClickAction(item.userId)
                }
            }
        }

        private fun ItemHistoryFinishExpandBinding.getLikeEvaluateList(item: EvaluateListModel) {
            compositeDisposable.add(
                RemoteDataSourceImpl().getEvaluateList(boardIdItem).applySchedulers()
                    .subscribe({ response ->
                        val updateItem = response.data.find { it == item }
                        if (updateItem != null) {
                            if (updateItem.isLike) {
                                ivLikes.setImageResource(R.drawable.icons_18_px_likes)
                                putLike(item.userId, false)
                            } else {
                                ivLikes.setImageResource(R.drawable.icons_18_px_likes_fill)
                                putLike(item.userId, true)
                            }

                        }
                    }, {})
            )
        }
        private fun putLike(userId: Long, like: Boolean) {
            compositeDisposable.add(
                RemoteDataSourceImpl().putEvaluateIsLike(boardIdItem, userId, like)
                    .applySchedulers()
                    .subscribe({}, {})
            )
        }
    }
}