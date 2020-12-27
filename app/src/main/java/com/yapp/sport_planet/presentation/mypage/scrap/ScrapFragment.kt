package com.yapp.sport_planet.presentation.mypage.scrap

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.FragmentScrapBinding
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.board.BoardActivity
import com.yapp.sport_planet.presentation.main.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class ScrapFragment : BaseFragment<FragmentScrapBinding, ScrapViewModel>(R.layout.fragment_scrap) {

    private val scrapAdapter: ScrapAdapter by lazy {
        ScrapAdapter({ boardId, boolean ->
            deleteBookMark(boardId, boolean)
        }, { goBoard(it) }).apply {
            setHasStableIds(true)
        }
    }
    override val viewModel: ScrapViewModel by lazy {
        ViewModelProvider(this).get(ScrapViewModel::class.java)
    }

    override fun init() {
        binding.run {
            rvScrap.adapter = scrapAdapter
            customToolBar.run {
                title.text = getString(R.string.fragment_my_page_bookmark)
                back.setOnClickListener {
                    onBackPressed()
                }
            }
            tvGoBoard.setOnClickListener {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.run {
            getBookMark()
            bookMarkList.observe(viewLifecycleOwner, Observer {
                binding.run {
                    if (it.isNotEmpty()) {
                        clEmpty.visibility = View.GONE
                        rvScrap.visibility = View.VISIBLE
                    }
                }
                scrapAdapter.setScrapItemList(it)
            })
            isLoading.observeOn(AndroidSchedulers.mainThread())
                .subscribe { if (it) showLoading() else hideLoading() }
                .addTo(compositeDisposable)
        }
    }

    private fun deleteBookMark(boardId: Long, boolean: Boolean) {
        if (!boolean) {
            binding.run {
                clEmpty.visibility = View.VISIBLE
                rvScrap.visibility = View.GONE
            }
        }
        viewModel.deleteBookMark(boardId)
    }

    private fun goBoard(boardId: Long) {
        BoardActivity.createInstance(activity!!, boardId = boardId)
    }

    companion object {
        fun newInstance() = ScrapFragment()
    }
}