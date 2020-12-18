package com.example.sport_planet.presentation.mypage.history.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivityHistoryBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.mypage.history.adapter.HistoryViewPager2Adapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class HistoryActivity : BaseActivity<ActivityHistoryBinding>(R.layout.activity_history) {

    private val tabTitles by lazy {
        listOf(
            getString(R.string.activity_history_tab_title_1),
            getString(R.string.activity_history_tab_title_2)
        )
    }
    private var tabNum: Int = 0

    private val vpAdapter by lazy {
        object : HistoryViewPager2Adapter(supportFragmentManager, lifecycle, tabTitles) {
            override fun createFragment(position: Int): Fragment =
                if (tabNum != 0) {
                    tabNum = 0
                    FinishTabFragment.newInstance()
                } else {
                    when (position) {
                        0 -> {
                            IngTabFragment.newInstance()
                        }
                        else -> {
                            FinishTabFragment.newInstance()
                        }
                    }
                }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tabNum = intent.getIntExtra("tab", 0)
        setTab()
        binding.customToolBar.run {
            back.setOnClickListener { finish() }
            title.text = getString(R.string.activity_history_title)
        }
    }

    private fun setTab() {
        binding.run {
            tlTab.tabGravity = TabLayout.GRAVITY_FILL
            vpBody.adapter = vpAdapter
            TabLayoutMediator(
                tlTab, vpBody, true
            ) { tab, position ->
                tab.text = vpAdapter.getItemTitle(position)
            }.attach()
        }
    }
}