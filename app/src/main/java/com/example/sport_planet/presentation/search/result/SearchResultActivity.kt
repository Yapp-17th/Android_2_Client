package com.example.sport_planet.presentation.search.result

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivitySearchResultBinding
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.search.adapter.SearchResultRecyclerAdapter

class SearchResultActivity :
    BaseActivity<ActivitySearchResultBinding>(R.layout.activity_search_result) {

    private lateinit var adapter: SearchResultRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (intent != null) {
            binding.tvSearch.text = intent.getStringExtra(SEARCH_STRING)
        }

        adapter = SearchResultRecyclerAdapter(
            itemClick = {},
            bookMarkClick = {}
        )


    }

    companion object {
        const val SEARCH_STRING = "SEARCH_STRING"
        fun createInstance(activity: Activity, searchString: String) {
            val intent = Intent(activity, SearchResultActivity::class.java)
            intent.putExtra(SEARCH_STRING, searchString)
        }
    }
}