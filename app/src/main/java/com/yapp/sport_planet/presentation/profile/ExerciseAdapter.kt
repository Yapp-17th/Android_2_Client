package com.yapp.sport_planet.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ItemExerciseRegionBinding

class ExerciseAdapter(private val onClickAction: (String,Long) -> Unit) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    private val items = mutableListOf<String>()
    private val selectItems = mutableListOf<String>()

    fun setItem(item: List<String>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = DataBindingUtil.inflate<ItemExerciseRegionBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_exercise_region,
            parent,
            false
        )
        return ExerciseViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ExerciseViewHolder(private val binding: ItemExerciseRegionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val item = items[adapterPosition]
                if (selectItems.contains(items[adapterPosition])) {
                    selectItems.remove(items[adapterPosition])
                    binding.tvText.setColorAndBackground(R.color.black, R.color.white)
                    onClickAction(item,adapterPosition+1L)
                } else {
                    if (selectItems.size < 3) {
                        selectItems.add(items[adapterPosition])
                        binding.tvText.setColorAndBackground(
                            R.color.dark_blue,
                            R.color.darkblue_opacity
                        )
                        onClickAction(item,adapterPosition+1L)
                    }
                }
            }
        }

        private fun TextView.setColorAndBackground(textColor: Int, backgroundColor: Int) {
            this.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    textColor
                )
            )
            this.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    backgroundColor
                )
            )
        }

        fun bind(item: String) {
            binding.item = item
        }
    }
}
