package com.yapp.sport_planet.presentation.write.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.CommonApiModel
import com.yapp.sport_planet.databinding.ItemSelectBinding

class SelectGridViewAdapter(
    private val onClick: (CommonApiModel) -> Unit
) : BaseAdapter() {
    private val items: ArrayList<CommonApiModel> = ArrayList()

    fun setItems(items: List<CommonApiModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): CommonApiModel {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemSelectBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent!!.context),
            R.layout.item_select,
            parent,
            false
        )
        val item = items[position]
        binding.tvTitle.text = item.name

        binding.root.setOnClickListener {
            onClick(item)
        }
        return binding.root
    }
}