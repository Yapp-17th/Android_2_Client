package com.example.sport_planet.presentation.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ItemCustomToolbarBinding
import com.example.sport_planet.data.enums.MenuEnum
import com.example.sport_planet.data.enums.SeparatorEnum
import com.example.sport_planet.util.Util

class CustomToolbar : ConstraintLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        getAttrs(attributeSet)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    ) {
        getAttrs(attributeSet)
    }

    private val binding: ItemCustomToolbarBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.item_custom_toolbar,
        this,
        true
    )

    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomToolbar)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        typedArray.getBoolean(R.styleable.CustomToolbar_isBackButton, false).run {
            setBackButtonVisible(this)
        }

        typedArray.getString(R.styleable.CustomToolbar_title)?.run {
            setTitle(this)
        }

        typedArray.recycle()
    }

    private val menuItems: ArrayList<MenuEnum> = ArrayList()

    fun setSeparator(item: SeparatorEnum) {
        when (item) {
            SeparatorEnum.Host, SeparatorEnum.Guest -> {
                binding.separator.visibility = View.VISIBLE
                binding.separator.text = item.name
                binding.separator.setTextColor(context.getColor(item.colorId))
            }
            else -> {
                binding.separator.visibility = View.GONE
            }
        }
    }

    fun setMenu(vararg item: MenuEnum) {
        item.forEach {
            if (!this.menuItems.contains(it)) {
                this.menuItems.add(it)
                CustomToolbarMenuItem(context).apply {
                    this.setImage(it.resourceId)
                    this.setClickListener { it.onClick }
                    binding.menu.addView(this)
                }
            }
        }
    }

    fun setTitle(title: String?) {
        if (binding.back.visibility == View.GONE && binding.separator.visibility == View.GONE) {
            (binding.title.layoutParams as LayoutParams).leftMargin =
                Util.dpToPx(context, 16.0f).toInt()
            binding.title.textSize = 20F
        }

        title?.run {
            binding.title.text = this
        }
    }

    fun setBackButtonVisible(visible: Boolean) {
        if (visible) {
            binding.back.visibility = View.VISIBLE
        } else {
            binding.back.visibility = View.GONE
        }
    }

    fun setBackButtonClick(onClickListener: OnClickListener) {
        binding.back.setOnClickListener(onClickListener)
    }
}