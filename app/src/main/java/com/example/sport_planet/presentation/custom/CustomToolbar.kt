package com.example.sport_planet.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ItemCustomToolbarBinding
import com.example.sport_planet.model.MenuEnum
import com.example.sport_planet.model.SeparatorEnum

class CustomToolbar : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private val binding: ItemCustomToolbarBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.item_custom_toolbar,
        this,
        false
    )
    private val menuItems: ArrayList<MenuEnum> = ArrayList()

    init {
        binding?.run {
            val layoutManager = LinearLayoutManager(context).apply {
                this.orientation = LinearLayoutManager.HORIZONTAL
            }
            this.menu.layoutManager = layoutManager
        }
    }

    fun backButtonVisible(visible: Boolean) {
        if (visible) {
            binding.back.visibility = View.VISIBLE
            binding.margin.visibility = View.GONE
        } else {
            binding.back.visibility = View.GONE
            binding.margin.visibility = View.VISIBLE
        }
    }

    fun setSeparator(item: SeparatorEnum) {
        when (item) {
            SeparatorEnum.HOST, SeparatorEnum.GUEST -> {
                binding.separator.visibility = View.VISIBLE
                binding.separator.text = "임시"
            }
            else -> {
                binding.separator.visibility = View.GONE
            }
        }
    }

    fun setTitle(title: String?) {
        title?.run {
            binding.title.text = this
        }
    }

    fun setMenu(vararg item: MenuEnum) {
        this.menuItems.clear()
        this.menuItems.addAll(item)
    }
}