package com.example.sport_planet.presentation.write.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.sport_planet.R
import com.example.sport_planet.data.model.ExerciseModel
import com.example.sport_planet.data.model.UserTagModel
import com.example.sport_planet.databinding.ItemWriteSelectBinding

class SelectGridViewAdapter<T> : BaseAdapter() {
    private val items: ArrayList<T> = ArrayList()

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): T {
        return items[position] as T
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemWriteSelectBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent!!.context),
            R.layout.item_write_filter,
            parent,
            false
        )
        val item = items[position]
        binding.tvTitle.text = when(item) {
            is ExerciseModel -> item.name
            is AddressCityModel -> item.name
            is UserTagModel -> item.name
            else -> ""
        }

        binding.root.setOnClickListener {

        }
        return binding.root
    }
}