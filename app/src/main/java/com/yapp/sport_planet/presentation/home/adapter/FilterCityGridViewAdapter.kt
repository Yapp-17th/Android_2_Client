package com.yapp.sport_planet.presentation.home.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.response.basic.RegionResponse
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterCityGridViewAdapter(
    private val clickItem: (RegionResponse.Data) -> Unit
) : BaseAdapter() {
    private val items: ArrayList<RegionResponse.Data> = ArrayList()
    private val selectedItem: ArrayList<RegionResponse.Data> = ArrayList()

    fun setItems(items: List<RegionResponse.Data>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setSelectedItems(items: List<RegionResponse.Data>) {
        this.selectedItem.clear()
        this.selectedItem.addAll(items)
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): RegionResponse.Data {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: View.inflate(parent?.context, R.layout.item_filter, null)
        val item = items[position]
        view.setOnClickListener {
            this.clickItem(item)
            notifyDataSetChanged()
        }

        view.setBackgroundResource(if (selectedItem.contains(item)) R.color.darkblue_opacity else R.color.white)

        view.tv_text.text = item.name

        view.iv_select.setImageResource(
            if (selectedItem.contains(item)) R.drawable.ic_start_blue_enabled else R.drawable.ic_star_disabled
        )

        return view
    }
}