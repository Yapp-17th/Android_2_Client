package com.yapp.sport_planet.presentation.mypage.history.view

import android.app.AlertDialog
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.mypage.MyViewHistoryModel
import com.yapp.sport_planet.data.request.EvaluateReportRequest
import com.yapp.sport_planet.databinding.FragmentFinishTabBinding
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.board.BoardActivity
import com.yapp.sport_planet.presentation.main.MainActivity
import com.yapp.sport_planet.presentation.mypage.history.ReportDialog
import com.yapp.sport_planet.presentation.mypage.history.adapter.FinishTabAdapter
import com.yapp.sport_planet.presentation.mypage.history.viewModel.FinishTabViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class FinishTabFragment :
    BaseFragment<FragmentFinishTabBinding, FinishTabViewModel>(R.layout.fragment_finish_tab) {
    private val finishTabAdapter: FinishTabAdapter by lazy {
        FinishTabAdapter({ myViewHistoryModel, boolean ->
            getApplyList(myViewHistoryModel, boolean)
        }, { showReportDialog(it) }).apply {
            setHasStableIds(true)
        }
    }
    override val viewModel: FinishTabViewModel by lazy {
        ViewModelProvider(this).get(FinishTabViewModel::class.java)
    }

    override fun init() {
        viewModel.getHistory()
        observeLiveData()
        binding.tvGoBoard.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun observeLiveData() {
        viewModel.myViewHistoryList.observe(viewLifecycleOwner, Observer {
            binding.run {
                rvHistoryFinish.adapter = finishTabAdapter.apply {
                    if (it.isNotEmpty()) {
                        clEmpty.visibility = View.GONE
                        rvHistoryFinish.visibility = View.VISIBLE
                        setMyViewHistoryItem(viewModel.myViewHistoryList.value!!)
                    }
                }
            }
        })
        viewModel.applyList.observe(viewLifecycleOwner, Observer {
            binding.run {
                finishTabAdapter.setApplyListItem(it)
            }
        })
        viewModel.isSuccess.observe(viewLifecycleOwner, Observer {
            if (it)
                showSuccessDialog()
        })
        viewModel.isLoading.observeOn(AndroidSchedulers.mainThread())
            .subscribe { if (it) showLoading() else hideLoading() }
            .addTo(compositeDisposable)
    }

    private fun getApplyList(myViewHistoryModel: MyViewHistoryModel, boolean: Boolean) {
        if (boolean) {
            val intent = Intent(requireContext(),BoardActivity::class.java)
            intent.putExtra("finishTabBoardId",myViewHistoryModel.boardInfo.boardId)
            startActivity(intent)
        } else {
            viewModel.getApplyList(myViewHistoryModel.boardInfo.boardId)
        }
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

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setView(R.layout.dialog_success)
            create()
            show()
        }
    }

    companion object {
        fun newInstance() = FinishTabFragment()
    }
}