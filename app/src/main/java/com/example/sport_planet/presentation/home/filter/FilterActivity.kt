package com.example.sport_planet.presentation.home.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivityFilterBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.home.adapter.FilterViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FilterActivity : BaseActivity<ActivityFilterBinding>(R.layout.activity_filter) {

    private lateinit var viewPagerAdapter: FilterViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewPagerAdapter = FilterViewPagerAdapter(supportFragmentManager, lifecycle)

        binding.toolbar.setBackButtonClick(View.OnClickListener { finish() })

        binding.vpFilterBody.adapter = viewPagerAdapter

        binding.btnOk.setOnClickListener {
            val intent = Intent()
            intent.putExtra(INTENT_CITY, viewPagerAdapter.getCity())
            intent.putExtra(INTENT_EXERCISE, viewPagerAdapter.getExercise())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.btnReset.setOnClickListener {
            viewPagerAdapter.resetFragment()
        }

        TabLayoutMediator(binding.tlFilterHeader, binding.vpFilterBody) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "지역"
                }
                1 -> {
                    tab.text = "운동"
                }
            }
        }.attach()
    }

    companion object {
        const val INTENT_CITY = "INTENT_CITY"
        const val INTENT_EXERCISE = "INTENT_EXERCISE"
    }
}