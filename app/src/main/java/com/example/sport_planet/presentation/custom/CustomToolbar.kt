package com.example.sport_planet.presentation.custom

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.sport_planet.R

class CustomToolbar : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context,attributeSet,defStyleAttr)

    init {
        inflate(context, R.layout.item_custom_toolbar, this)
    }

    fun setBackButton(visible:Boolean) {

    }

    fun setTitle(title:String) {

    }

    fun addItem(vararg item:String) {

    }
}