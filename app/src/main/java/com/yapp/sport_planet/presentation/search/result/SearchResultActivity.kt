package com.yapp.sport_planet.presentation.search.result

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ActivitySearchResultBinding
import com.yapp.sport_planet.presentation.base.BaseActivity
import com.yapp.sport_planet.presentation.board.BoardActivity
import com.yapp.sport_planet.presentation.search.adapter.SearchResultRecyclerAdapter
import com.yapp.sport_planet.remote.RemoteDataSourceImpl

class SearchResultActivity :
    BaseActivity<ActivitySearchResultBinding>(R.layout.activity_search_result) {

    private lateinit var adapter: SearchResultRecyclerAdapter
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            SearchResultViewModelFactory(RemoteDataSourceImpl())
        ).get(SearchResultViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.keyword.observe(this, Observer {
            binding.tvSearch.text = it
            viewModel.getSearchList()
        })

        viewModel.boardList.observe(this, Observer {
            adapter.setItems(it)
        })

        adapter = SearchResultRecyclerAdapter(
            itemClick = {
                BoardActivity.createInstance(this, it)
            },
            bookMarkClick = {
                viewModel.bookmarkChange(it)
            }
        )
        binding.rvSearchList.adapter = adapter

        if (intent != null) {
            viewModel.keyword.value = intent.getStringExtra(SEARCH_STRING)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getSearchList()
    }

    companion object {
        const val SEARCH_STRING = "SEARCH_STRING"
        fun createInstance(activity: Activity, searchString: String) {
            val intent = Intent(activity, SearchResultActivity::class.java)
            intent.putExtra(SEARCH_STRING, searchString)
            activity.startActivity(intent)
        }
    }
}