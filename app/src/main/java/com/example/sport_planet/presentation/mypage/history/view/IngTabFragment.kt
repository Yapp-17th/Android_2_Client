package com.example.sport_planet.presentation.mypage.history.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.data.model.MyViewHistoryModel
import com.example.sport_planet.databinding.FragmentIngTabBinding
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.mypage.history.adapter.IngTabAdapter
import com.example.sport_planet.presentation.mypage.history.viewModel.IngTabViewModel

class IngTabFragment :
    BaseFragment<FragmentIngTabBinding, IngTabViewModel>(R.layout.fragment_ing_tab) {
    private val ingTabAdapter: IngTabAdapter by lazy {
            IngTabAdapter(::getApplyList).apply {
                setHasStableIds(true)
            }
    }
    override val viewModel: IngTabViewModel by lazy {
        ViewModelProvider(this).get(IngTabViewModel::class.java)
    }

    override fun init() {
        viewModel.getHistory()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.myViewHistoryList.observe(viewLifecycleOwner, Observer {
            binding.run {
                rvHistoryIng.adapter = ingTabAdapter.apply {
                        setMyViewHistoryItem(viewModel.myViewHistoryList.value!!)
                    }
            }
        })
        viewModel.applyList.observe(viewLifecycleOwner, Observer {
            binding.run {
                    ingTabAdapter.setApplyListItem(it)
            }
        })
    }

    private fun getApplyList(myViewHistoryModel: MyViewHistoryModel) {
        viewModel.getApplyList(myViewHistoryModel.boardInfo.boardId)
    }

    companion object {
        fun newInstance() = IngTabFragment()
    }
}