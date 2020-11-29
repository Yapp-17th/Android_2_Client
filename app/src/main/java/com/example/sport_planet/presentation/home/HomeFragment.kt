package com.example.sport_planet.presentation.home

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.MenuEnum
import com.example.sport_planet.data.enums.SeparatorEnum
import com.example.sport_planet.data.model.BoardModel
import com.example.sport_planet.databinding.FragmentHomeBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.home.adapter.BookMarkClickListener
import com.example.sport_planet.presentation.home.adapter.HomeRecyclerAdapter
import com.example.sport_planet.remote.RemoteDataSourceImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo


class HomeFragment private constructor() :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(R.layout.fragment_home) {
    companion object {
        fun newInstance() = HomeFragment()
    }

    override val viewModel: HomeViewModel
            by lazy {
                ViewModelProvider(
                    this,
                    HomeViewModelFactory(RemoteDataSourceImpl())
                ).get(HomeViewModel::class.java)
            }

    private val adapter =
        HomeRecyclerAdapter(
            View.OnClickListener { },
            object : BookMarkClickListener {
                override fun onClick(item: BoardModel) {
                    viewModel.bookmarkChange(item)
                }
            })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.runOnUiThread {
            binding.toolbar.run {
                binding.toolbar.setSeparator(SeparatorEnum.Guest)
                binding.toolbar.setMenu(MenuEnum.MENU)
            }
        }
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
                    viewModel.addBoardNextPage()
                }
            }
        })

        viewModel.isLoading
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { if (it) showLoading() else hideLoading() }
            .addTo(compositeDisposable)

        viewModel.boardList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) adapter.setItems(it)
        })

        viewModel.showRecyclerViewRefresh
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter.notifyDataSetChanged() }
            .addTo(compositeDisposable)

        registerForContextMenu(binding.clFilterTime)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getBoardList()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        activity?.run {
            this.menuInflater.inflate(R.menu.menu_home_filter_time, menu)
        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.filter_recent -> {

            }
            R.id.filter_person -> {

            }
            R.id.filter_time -> {

            }
        }
        return true
    }

    override fun init() {
    }
}