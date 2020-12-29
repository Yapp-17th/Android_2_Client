package com.yapp.sport_planet.presentation.mypage.history.adapter

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

abstract class HistoryViewPager2Adapter(
    fm: FragmentManager,
    lifecycle: Lifecycle,
    private val titles: List<String>
) : FragmentStateAdapter(fm, lifecycle)
    {
fun getItemTitle(position: Int): CharSequence =
        titles[position]

    override fun getItemCount(): Int =
        titles.size
}