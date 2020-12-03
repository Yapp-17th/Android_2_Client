package com.example.sport_planet.presentation.home.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.example.sport_planet.R
import com.example.sport_planet.data.response.basic.ExerciseResponse
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterExerciseGridViewAdapter : BaseAdapter() {
    private val items: ArrayList<ExerciseResponse.Data> = ArrayList()
    private val selectedItem: ArrayList<ExerciseResponse.Data> = ArrayList()

    fun setItems(item: List<ExerciseResponse.Data>) {
        items.clear()
        items.addAll(item)
        notifyDataSetChanged()
    }

    fun getSelectItemsId(): String {
        return selectedItem.map { it.id }.joinToString(",")
    }

    fun clearSelectItem() {
        selectedItem.clear()
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): ExerciseResponse.Data {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: View.inflate(parent?.context, R.layout.item_filter, null)
        val item = items[position]
        view.setOnClickListener {
            if (!selectedItem.contains(item) && selectedItem.size >= 3) {
                Toast.makeText(view.context, "최대 3개 선택 가능", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (selectedItem.contains(item)) selectedItem.remove(item) else selectedItem.add(item)
            notifyDataSetChanged()
        }

        view.setBackgroundResource(if (selectedItem.contains(item)) R.color.darkblue_opacity else R.color.white)

        view.tv_text.text = item.name

        view.iv_select.setImageResource(
            if (selectedItem.contains(item)) R.drawable.ic_star_enabled else R.drawable.ic_star_disabled
        )

        return view
    }
}