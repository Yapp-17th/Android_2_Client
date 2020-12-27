package com.yapp.sport_planet.presentation.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.ActivitySearchBinding
import com.yapp.sport_planet.presentation.base.BaseActivity
import com.yapp.sport_planet.presentation.search.adapter.SearchRecyclerAdapter
import com.yapp.sport_planet.presentation.search.result.SearchResultActivity


class SearchActivity : BaseActivity<ActivitySearchBinding>(R.layout.activity_search) {

    private lateinit var adapter: SearchRecyclerAdapter

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            SearchViewModelFactory(getSharedPreferences("search", MODE_PRIVATE))
        ).get(SearchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = SearchRecyclerAdapter(
            itemClick = {
                SearchResultActivity.createInstance(this, it)
            },
            xClick = {
                viewModel.deleteRecentSearchList(it)
            }
        )
        binding.rvSearchList.adapter = adapter

        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.tvDeleteSearchList.setOnClickListener {
            viewModel.clearRecentSearchList()
        }

        viewModel.itemList.observe(this, Observer {
            adapter.setItems(it)
        })

        binding.edtSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val text = v.text.toString()
                if (text.isEmpty()) {
                    showToast("검색어를 입력해 주세요")
                    return@OnEditorActionListener true
                }
                viewModel.putRecentSearchList(text)
                SearchResultActivity.createInstance(this, text)
                return@OnEditorActionListener true
            }
            false
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getRecentSearchList()
    }

    companion object {
        fun createInstance(activity: Activity) {
            activity.startActivity(Intent(activity, SearchActivity::class.java))
        }
    }
}