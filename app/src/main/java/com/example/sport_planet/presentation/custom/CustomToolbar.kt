package com.example.sport_planet.presentation.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R

class CustomToolbar : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attributeSet,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.item_custom_toolbar, this)
    }

    private val back: ImageView = rootView.findViewById(R.id.back)
    private val margin: TextView = rootView.findViewById(R.id.margin)
    private val separator: TextView = rootView.findViewById(R.id.separator)
    private val title: TextView = rootView.findViewById(R.id.title)
    private val menu: RecyclerView = rootView.findViewById(R.id.menu)

    private val menuItems: ArrayList<String> = ArrayList()

    fun backButtonVisible(visible: Boolean) {
        if (visible) {
            this.back.visibility = View.VISIBLE
            this.margin.visibility = View.GONE
        } else {
            this.back.visibility = View.GONE
            this.margin.visibility = View.VISIBLE
        }
    }

    fun setSeparator() {

    }

    fun setTitle(title: String) {
        this.title.text = title
    }

    fun setMenu(vararg item: String) {
        this.menuItems.clear()
        this.menuItems.addAll(item)
    }
}