package com.yapp.sport_planet.presentation.write.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.enums.WriteFilterEnum
import com.yapp.sport_planet.data.model.CommonApiModel
import com.yapp.sport_planet.databinding.ItemWriteFilterBinding
import com.yapp.sport_planet.presentation.write.DataChangeListener
import com.yapp.sport_planet.presentation.write.select.SelectFragment
import com.yapp.sport_planet.presentation.write.select.SelectItemListener

class WriteGridViewAdapter(
    private val fm: FragmentManager,
    private val dataChangeListener: DataChangeListener
) : BaseAdapter() {
    private val items: List<WriteFilterEnum> =
        listOf(WriteFilterEnum.EXERCISE, WriteFilterEnum.CITY, WriteFilterEnum.USERTAG)
    private var result: ArrayList<Pair<WriteFilterEnum, CommonApiModel?>> = arrayListOf(
        Pair(WriteFilterEnum.EXERCISE, null),
        Pair(WriteFilterEnum.CITY, null),
        Pair(WriteFilterEnum.USERTAG, null)
    )

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
        val isChecked = result[position].second != null
        binding.tvTitle.text =
            if (isChecked) result[position].second!!.name else getItem(position).title
        binding.ivToggle.setImageResource(if (isChecked) R.drawable.icons_18_px_x else R.drawable.ic_toggle_off)
        binding.root.setBackgroundResource(if (isChecked) R.drawable.shape_round_corner_into_dark_blue_opacity else R.drawable.shape_round_corner)
        binding.root.setOnClickListener {
            if (isChecked) {
                result[position] = Pair(items[position], null)
            } else {
                val fragment = SelectFragment.newInstance(items[position])
                fragment.setListener(object : SelectItemListener {
                    override fun confirm(model: CommonApiModel) {
                        result[position] = Pair(items[position], model)
                        dataChangeListener.onChange(result = result[position])
                        notifyDataSetChanged()
                    }
                })
                fragment.show(fm, "SELECT")
            }
        }
        return binding.root
    }

}