package com.yapp.sport_planet.presentation.home.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.response.basic.ExerciseResponse
import com.yapp.sport_planet.data.response.basic.RegionResponse
import com.yapp.sport_planet.databinding.ActivityFilterBinding
import com.yapp.sport_planet.presentation.base.BaseActivity
import com.yapp.sport_planet.presentation.home.HomeFragment
import com.yapp.sport_planet.presentation.home.adapter.FilterViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class FilterActivity : BaseActivity<ActivityFilterBinding>(R.layout.activity_filter) {

    private lateinit var viewPagerAdapter: FilterViewPagerAdapter
    private val viewModel by lazy {
        ViewModelProvider(this).get(FilterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent?.run {
            viewModel.city.value = this.getParcelableArrayListExtra(INTENT_CITY)
            viewModel.exercise.value = this.getParcelableArrayListExtra(INTENT_EXERCISE)
        }

        viewPagerAdapter = FilterViewPagerAdapter(
            fragmentManager = supportFragmentManager,
            city = viewModel.city.value ?: emptyList(),
            exercise = viewModel.exercise.value ?: emptyList(),
            lifecycle = lifecycle
        )

        binding.toolbar.setBackButtonClick(View.OnClickListener { finish() })

        binding.vpFilterBody.adapter = viewPagerAdapter

        binding.btnOk.setOnClickListener {
            val intent = Intent()
            intent.putParcelableArrayListExtra(
                INTENT_CITY,
                ArrayList(viewModel.city.value ?: emptyList())
            )
            intent.putParcelableArrayListExtra(
                INTENT_EXERCISE,
                ArrayList(viewModel.exercise.value ?: emptyList())
            )
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

    fun getCity(list: List<RegionResponse.Data>) {
        viewModel.city.value = list
    }

    fun getExercise(list: List<ExerciseResponse.Data>) {
        viewModel.exercise.value = list
    }

    companion object {
        const val INTENT_CITY = "INTENT_CITY"
        const val INTENT_EXERCISE = "INTENT_EXERCISE"

        fun createInstance(
            fragment: Fragment,
            city: List<RegionResponse.Data> = emptyList(),
            exercise: List<ExerciseResponse.Data> = emptyList()
        ) {
            val intent = Intent(fragment.context, FilterActivity::class.java)
            intent.putParcelableArrayListExtra(INTENT_CITY, ArrayList(city))
            intent.putParcelableArrayListExtra(INTENT_EXERCISE, ArrayList(exercise))
            fragment.startActivityForResult(intent, HomeFragment.FILTER_REQUEST_CODE)
        }
    }
}