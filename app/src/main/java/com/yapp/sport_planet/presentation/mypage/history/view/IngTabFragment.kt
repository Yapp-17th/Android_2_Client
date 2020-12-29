package com.yapp.sport_planet.presentation.mypage.history.view

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.chatting.ChatRoomInfo
import com.yapp.sport_planet.data.model.mypage.ApplyListModel
import com.yapp.sport_planet.data.model.mypage.MyViewHistoryModel
import com.yapp.sport_planet.databinding.FragmentIngTabBinding
import com.yapp.sport_planet.presentation.base.BaseAcceptCancelDialog
import com.yapp.sport_planet.presentation.base.BaseFragment
import com.yapp.sport_planet.presentation.chatting.UserInfo
import com.yapp.sport_planet.presentation.chatting.view.ChattingActivity
import com.yapp.sport_planet.presentation.main.MainActivity
import com.yapp.sport_planet.presentation.mypage.history.adapter.IngTabAdapter
import com.yapp.sport_planet.presentation.mypage.history.viewModel.IngTabViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo

class IngTabFragment :
    BaseFragment<FragmentIngTabBinding, IngTabViewModel>(R.layout.fragment_ing_tab) {
    private val ingTabAdapter: IngTabAdapter by lazy {
        IngTabAdapter({ getApplyList(it) }, { showChattingRoomDialog(it) }).apply {
            setHasStableIds(true)
        }
    }

    override val viewModel: IngTabViewModel by lazy {
        ViewModelProvider(this).get(IngTabViewModel::class.java)
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
        viewModel.run {
            binding.run {
                myViewHistoryList.observe(viewLifecycleOwner, Observer {
                        rvHistoryIng.adapter = ingTabAdapter.apply {
                            if(it.isNotEmpty()) {
                                clEmpty.visibility = View.GONE
                                rvHistoryIng.visibility = View.VISIBLE
                                setMyViewHistoryItem(viewModel.myViewHistoryList.value!!)
                            }
                    }
                })
                applyList.observe(viewLifecycleOwner, Observer {
                        ingTabAdapter.setApplyListItem(it)
                })
                isLoading.observeOn(AndroidSchedulers.mainThread())
                    .subscribe { if (it) showLoading() else hideLoading() }
                    .addTo(compositeDisposable)
            }
        }
    }

    private fun showChattingRoomDialog(applyListModel: ApplyListModel) {
        val dialog = BaseAcceptCancelDialog.newInstance(
            dialogTitleText = "",
            dialogBodyText = getString(R.string.dialog_chatting_room_body),
            dialogAcceptText = getString(R.string.dialog_chatting_room_ok)
        )
        dialog.setAcceptCancelDialogListener(object :
            BaseAcceptCancelDialog.AcceptCancelDialogListener {
            override fun onAccept() {
                val intent = Intent(context, ChattingActivity::class.java)
                intent.putExtra(
                    "chatRoomInfo",
                    ChatRoomInfo(
                        applyListModel.chattingRoomId,
                        applyListModel.boardId,
                        applyListModel.guestId,
                        applyListModel.hostId == UserInfo.USER_ID,
                        applyListModel.guestName
                    )
                )
                startActivity(intent)
            }
        })
        dialog.show(parentFragmentManager, "")
    }

    private fun getApplyList(myViewHistoryModel: MyViewHistoryModel) {
        viewModel.getApplyList(myViewHistoryModel.boardInfo.boardId)
    }

    companion object {
        fun newInstance() = IngTabFragment()
    }
}