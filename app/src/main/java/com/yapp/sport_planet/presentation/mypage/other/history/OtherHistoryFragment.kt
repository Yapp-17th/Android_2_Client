package com.yapp.sport_planet.presentation.mypage.other.history

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.databinding.FragmentOtherHistoryBinding
import com.yapp.sport_planet.presentation.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.item_custom_toolbar.*

class OtherHistoryFragment :
    BaseFragment<FragmentOtherHistoryBinding, OtherHistoryViewModel>(R.layout.fragment_other_history) {
    override val viewModel: OtherHistoryViewModel by lazy {
        ViewModelProvider(this).get(OtherHistoryViewModel::class.java)
    }

    private val otherHistoryAdapter = OtherHistoryAdapter()

    override fun init() {
        binding.rvHistory.adapter = otherHistoryAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val userId = arguments?.getLong("userId")
        userId?.let { viewModel.getOtherHistory(it) }
        binding.customToolBar.run {
            setTitle(getString(R.string.fragment_my_page_history))
            back.setOnClickListener { onBackPressed() }
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.run {
            otherHistoryModel.observe(viewLifecycleOwner, Observer {
                otherHistoryAdapter.setOtherHistoryItem(it)
            })
            isLoading.observeOn(AndroidSchedulers.mainThread())
                .subscribe { if(it) showLoading() else hideLoading() }
                .addTo(compositeDisposable)
        }
    }

    companion object {
        fun instance(userId: Long) = OtherHistoryFragment().apply {
            arguments = Bundle().apply {
                putLong("userId", userId)
            }
        }
    }
}