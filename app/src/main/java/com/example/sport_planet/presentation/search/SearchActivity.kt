package com.example.sport_planet.presentation.search

import android.os.Bundle
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivitySearchBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.search.adapter.SearchRecyclerAdapter

class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private lateinit var adapter: SearchRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchRecyclerAdapter()
        binding.rvSearchList.adapter = adapter
    }
}