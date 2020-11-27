package com.example.sport_planet.presentation.mypage.history

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.data.model.MyViewHistoryModel
import com.example.sport_planet.data.request.EvaluateReportRequest
import com.example.sport_planet.databinding.FragmentFinishTabBinding
import com.example.sport_planet.presentation.base.BaseFragment

class FinishTabFragment :
    BaseFragment<FragmentFinishTabBinding, FinishTabViewModel>(R.layout.fragment_finish_tab) {
    private val finishTabAdapter: FinishTabAdapter by lazy {
        FinishTabAdapter({ getApplyList(it) }, { showReportDialog(it) }).apply {
            setHasStableIds(true)
        }
    }
    override val viewModel: FinishTabViewModel by lazy {
        ViewModelProvider(this).get(FinishTabViewModel::class.java)
    }

    override fun init() {
        viewModel.getHistory()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.myViewHistoryList.observe(viewLifecycleOwner, Observer {
            binding.run {
                rvHistoryFinish.adapter = finishTabAdapter.apply {
                    setMyViewHistoryItem(viewModel.myViewHistoryList.value!!)
                }
            }
        })
        viewModel.applyList.observe(viewLifecycleOwner, Observer {
            binding.run {
                finishTabAdapter.setApplyListItem(it)
            }
        })
    }

    private fun getApplyList(myViewHistoryModel: MyViewHistoryModel) {
        viewModel.getApplyList(myViewHistoryModel.boardInfo.boardId)
    }

    private fun showReportDialog(userId: Long) {
        val dialog = ReportDialog.newInstance()
        dialog.setReportDialogListener(object : ReportDialog.ReportDialogListener {
            override fun onAccept(index: Long, content: String?) {
                viewModel.postReport(EvaluateReportRequest(userId, index, content))
            }
        })
        dialog.show(parentFragmentManager, "")
    }

    companion object {
        fun newInstance() = FinishTabFragment()
    }
}