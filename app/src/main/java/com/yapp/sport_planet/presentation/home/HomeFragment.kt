package com.yapp.sport_planet.presentation.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.enums.TimeFilterEnum
import com.yapp.sport_planet.databinding.FragmentHomeBinding
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.board.BoardActivity
import com.yapp.sport_planet.presentation.home.adapter.HomeRecyclerAdapter
import com.yapp.sport_planet.presentation.home.filter.FilterActivity
import com.yapp.sport_planet.presentation.home.filter.FilterActivity.Companion.INTENT_CITY
import com.yapp.sport_planet.presentation.home.filter.FilterActivity.Companion.INTENT_EXERCISE
import com.yapp.sport_planet.presentation.main.MainActivity
import com.yapp.sport_planet.presentation.search.SearchActivity
import com.yapp.sport_planet.remote.RemoteDataSourceImpl
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

        viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { if (it) showLoading() else hideLoading() }
            .addTo(compositeDisposable)

        viewModel.showRecyclerViewRefresh
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter.notifyDataSetChanged() }
            .addTo(compositeDisposable)

        viewModel.showDataIsEmpty
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (it) {
                    binding.clBoardEmpty.visibility = View.VISIBLE
                    binding.recBoard.visibility = View.GONE
                } else {
                    binding.clBoardEmpty.visibility = View.GONE
                    binding.recBoard.visibility = View.VISIBLE
                }
            }.addTo(compositeDisposable)

        viewModel.showSearchActivity
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showSearchActivity() }
            .addTo(compositeDisposable)

        viewModel.showCityExerciseFilterActivity
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showCityExerciseFilterActivity() }
            .addTo(compositeDisposable)

        viewModel.showTimeFilterPopup
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { showTimeFilterPopup() }
            .addTo(compositeDisposable)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBoardList()
    }

    override fun init() {
    }

    private fun showSearchActivity() {
        SearchActivity.createInstance(activity!!)
    }

    private fun showCityExerciseFilterActivity() {
        FilterActivity.createInstance(
            fragment = this,
            city = viewModel.city,
            exercise = viewModel.exercise
        )
    }

    private fun showTimeFilterPopup() {
        activity?.let { activity ->
            val view = binding.clFilterTime
            val popup = PopupMenu(activity.applicationContext, view)
            activity.menuInflater.inflate(R.menu.menu_home_filter_time, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.filter_latest -> {
                        TimeFilterEnum.TIME_LATEST.run {
                            viewModel.time = this
                            view.tv_time_text.text = this.print
                            viewModel.getBoardList()
                        }
                        false
                    }
                    R.id.filter_remain -> {
                        TimeFilterEnum.TIME_REMAIN.run {
                            viewModel.time = this
                            view.tv_time_text.text = this.print
                            viewModel.getBoardList()
                        }
                        false
                    }
                    R.id.filter_daedline -> {
                        TimeFilterEnum.TIME_DEADLINE.run {
                            viewModel.time = this
                            view.tv_time_text.text = this.print
                            viewModel.getBoardList()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILTER_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.city =
                    data?.getParcelableArrayListExtra(INTENT_CITY) ?: emptyList()
                viewModel.exercise =
                    data?.getParcelableArrayListExtra(INTENT_EXERCISE) ?: emptyList()
            }
        } else if (requestCode == REFRESH) {
            if (resultCode == Activity.RESULT_OK) {
                (activity as? MainActivity)?.showSuccessDialog()
                viewModel.getBoardList()
            }
        }
    }

    companion object {
        const val FILTER_REQUEST_CODE = 1
        const val REFRESH = 2

        fun newInstance() = HomeFragment()
    }
}