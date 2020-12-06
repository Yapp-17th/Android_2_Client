package com.example.sport_planet.presentation.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.TimeFilterEnum
import com.example.sport_planet.databinding.FragmentHomeBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.board.BoardActivity
import com.example.sport_planet.presentation.home.adapter.HomeRecyclerAdapter
import com.example.sport_planet.presentation.home.filter.FilterActivity
import com.example.sport_planet.presentation.home.filter.FilterActivity.Companion.INTENT_CITY
import com.example.sport_planet.presentation.home.filter.FilterActivity.Companion.INTENT_EXERCISE
import com.example.sport_planet.presentation.search.SearchActivity
import com.example.sport_planet.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_home_filter_time.view.*


class HomeFragment private constructor() :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    override val viewModel: HomeViewModel
            by lazy {
                ViewModelProvider(
                    this,
                    HomeViewModelFactory(RemoteDataSourceImpl())
                ).get(HomeViewModel::class.java)
            }

    private lateinit var adapter: HomeRecyclerAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeRecyclerAdapter(
            { BoardActivity.createInstance(activity!!, it) },
            { viewModel.bookmarkChange(it) }
        )

        binding.vm = viewModel

        binding.recBoard.adapter = adapter
        binding.recBoard.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val rec = binding.recBoard
                var lastVisibleItemPosition =
                    (rec.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                var itemTotalCount =
                    (rec.adapter as HomeRecyclerAdapter).itemCount

                if (lastVisibleItemPosition + 1 == itemTotalCount) {
                    viewModel.addPageCount()
                }
            }
        })

        binding.llToolbar.setOnClickListener {
            SearchActivity.createInstance(activity!!)
        }

        binding.clFilterOption.setOnClickListener {
            Intent(activity, FilterActivity::class.java).apply {
                startActivityForResult(this, FILTER_REQUEST_CODE)
            }
        }

        binding.clFilterTime.setOnClickListener {
            activity?.let { activity ->
                val popup = PopupMenu(activity.applicationContext, it)
                activity.menuInflater.inflate(R.menu.menu_home_filter_time, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.filter_latest -> {
                            TimeFilterEnum.TIME_LATEST.run {
                                viewModel.changeTimeFilter(this)
                                it.tv_time_text.text = this.print
                            }
                            false
                        }
                        R.id.filter_remain -> {
                            TimeFilterEnum.TIME_REMAIN.run {
                                viewModel.changeTimeFilter(this)
                                it.tv_time_text.text = this.print
                            }
                            false
                        }
                        R.id.filter_daedline -> {
                            TimeFilterEnum.TIME_DEADLINE.run {
                                viewModel.changeTimeFilter(this)
                                it.tv_time_text.text = this.print
                            }
                            false
                        }
                        else -> {
                            false
                        }
                    }
                }
                popup.show()
            }
        }

        viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { if (it) showLoading() else hideLoading() }
            .addTo(compositeDisposable)

        viewModel.showRecyclerViewRefresh
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter.notifyDataSetChanged() }
            .addTo(compositeDisposable)
    }

    override fun init() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILTER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.city.value = data?.getStringExtra(INTENT_CITY) ?: "0"
                viewModel.exercise.value = data?.getStringExtra(INTENT_EXERCISE) ?: "0"
                viewModel.getBoardList()
            }
        } else if (requestCode == REFRESH) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.getBoardList()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBoardList()
    }

    companion object {
        const val FILTER_REQUEST_CODE = 1
        const val REFRESH = 2

        fun newInstance() = HomeFragment()
    }
}