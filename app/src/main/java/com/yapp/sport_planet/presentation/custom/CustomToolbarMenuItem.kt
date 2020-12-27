package com.yapp.sport_planet.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ItemCustomToolbarMenuBinding

class CustomToolbarMenuItem : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    private val binding: ItemCustomToolbarMenuBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.item_custom_toolbar_menu,
        this,
        true
    )

    fun setImage(resourceId: Int) {
        binding.icon.setImageResource(resourceId)
    }

    fun setClickListener(function: () -> Unit) {
        binding.root.setOnClickListener {
            function
        }
    }
}