package com.yapp.sport_planet.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ItemExerciseListBinding
import kotlinx.android.synthetic.main.item_exercise_list.view.*

class ExerciseListAdapter(private val onClickAction: (String,Int) -> Unit) :
    RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder>() {
    private val items = mutableListOf<String>()

    fun setItem(item: List<String>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewHolder {
        val binding = DataBindingUtil.inflate<ItemExerciseListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_exercise_list,
            parent,
            false
        )
        return ExerciseListViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ExerciseListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ExerciseListViewHolder(private val binding: ItemExerciseListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.iv_x.setOnClickListener {
                val item = items[adapterPosition]
                val id = adapterPosition
                items.remove(item)
                notifyDataSetChanged()
                onClickAction(item,id)
            }
        }

        fun bind(item: String) {
            binding.item = item
        }
    }
}