package com.example.sport_planet.presentation.home.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bind_setHomeItems")
fun setHomeItems(view: RecyclerView, items: List<String>?) {
    items?.run {
        (view.adapter as HomeRecyclerAdapter).setItems(items)
    }
}