package com.example.sport_planet.presentation.mypage.history

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.data.response.EvaluateListResponse
import com.example.sport_planet.databinding.ItemHistoryFinishExpandBinding
import com.example.sport_planet.remote.RemoteDataSourceImpl
import com.example.sport_planet.util.applySchedulers
import io.reactivex.disposables.CompositeDisposable

class FinishTabExpandAdapter(private val onClickAction: (Long) -> Unit) :
    RecyclerView.Adapter<FinishTabExpandAdapter.FinishTabExpandViewHolder>() {

    val applyListItem = mutableListOf<EvaluateListResponse.EvaluateListModel>()
    var boardIdItem: Long = 0L
    private val compositeDisposable = CompositeDisposable()

    fun setApplyListItem(item: List<EvaluateListResponse.EvaluateListModel>) {
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
    ): FinishTabExpandAdapter.FinishTabExpandViewHolder {
        val binding = DataBindingUtil.inflate<ItemHistoryFinishExpandBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_history_finish_expand, parent, false
        )
        return FinishTabExpandViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: FinishTabExpandAdapter.FinishTabExpandViewHolder,
        position: Int
    ) {
        holder.bind(applyListItem[position])
    }

    override fun getItemCount(): Int = applyListItem.size

    inner class FinishTabExpandViewHolder(private val binding: ItemHistoryFinishExpandBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EvaluateListResponse.EvaluateListModel) {
            binding.items = item

            binding.ivLikes.setOnClickListener {
                compositeDisposable.add(
                    RemoteDataSourceImpl().getEvaluateList2(boardIdItem).applySchedulers()
                        .subscribe({ response ->
                            val updateItem = response.data.find { it == item }
                            if (updateItem != null) {
                                if (updateItem.isLike && !updateItem.isDislike) {
                                    binding.ivLikes.setImageResource(R.drawable.icons_18_px_likes)
                                    binding.ivDisLikes.setImageResource(R.drawable.icons_18_px_dislikes_fill)
                                    putLike(item.userId, false)
                                } else {
                                    binding.ivLikes.setImageResource(R.drawable.icons_18_px_likes_fill)
                                    binding.ivDisLikes.setImageResource(R.drawable.icons_18_px_dislikes)
                                    putLike(item.userId, true)
                                }

                            }
                        }, {})
                )

            }
            binding.ivDisLikes.setOnClickListener {
                compositeDisposable.add(
                    RemoteDataSourceImpl().getEvaluateList2(boardIdItem).applySchedulers()
                        .subscribe({ response ->
                            val updateItem = response.data.find { it == item }
                            if (updateItem != null) {
                                if (updateItem.isDislike && !updateItem.isLike) {
                                    binding.ivLikes.setImageResource(R.drawable.icons_18_px_likes_fill)
                                    binding.ivDisLikes.setImageResource(R.drawable.icons_18_px_dislikes)
                                    putLike(item.userId, true)
                                } else {
                                    binding.ivLikes.setImageResource(R.drawable.icons_18_px_likes)
                                    binding.ivDisLikes.setImageResource(R.drawable.icons_18_px_dislikes_fill)
                                    putLike(item.userId, false)
                                }
                            }
                        }, {})
                )

            }
            binding.tvReport.setOnClickListener {
                onClickAction(item.userId)
            }
        }
        private fun putLike(userId: Long, like: Boolean) {
            compositeDisposable.add(
                RemoteDataSourceImpl().putEvaluateIsLike2(boardIdItem, userId, like)
                    .applySchedulers()
                    .subscribe({}, {})
            )
        }
    }
}