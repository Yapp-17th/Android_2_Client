package com.example.sport_planet.presentation.write.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.WriteFilterEnum
import com.example.sport_planet.databinding.ItemWriteFilterBinding

class WriteGridViewAdapter : BaseAdapter() {
    private val items: List<WriteFilterEnum> =
        listOf(WriteFilterEnum.EXERCISE, WriteFilterEnum.ADDRESS, WriteFilterEnum.USERTAG)
    private var result: ArrayList<String> = arrayListOf("", "", "")

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): WriteFilterEnum {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemWriteFilterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent!!.context),
            R.layout.item_write_filter,
            parent,
            false
        )
        val isChecked = !result[position].isNullOrEmpty()
        binding.tvTitle.text = if (isChecked) result[position] else getItem(position).title
        binding.ivToggle.setImageResource(if (isChecked) R.drawable.icons_18_px_x else R.drawable.ic_toggle_off)
        binding.root.setBackgroundResource(if (isChecked) R.drawable.shape_round_corner_into_darkblue_opacity else R.drawable.shape_round_corner_into_white)
        binding.root.setOnClickListener {
            if (isChecked) {

            } else {
                result[position] = ""
            }
        }
        return binding.root
    }

}