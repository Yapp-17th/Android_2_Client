package com.yapp.sport_planet.presentation.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ItemMyPageExerciseListBinding

class MyPageExerciseListAdapter :
    RecyclerView.Adapter<MyPageExerciseListAdapter.MyPageExerciseListViewHolder>() {

    private val items = mutableListOf<String>()

    fun setItem(item: List<String>) {
        items.clear()
        items.addAll(item.sorted())
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPageExerciseListViewHolder {
        val binding = DataBindingUtil.inflate<ItemMyPageExerciseListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_my_page_exercise_list,
            parent,false
        )
        return MyPageExerciseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPageExerciseListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class MyPageExerciseListViewHolder(private val binding: ItemMyPageExerciseListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item : String){
            binding.item = item
        }
    }
}