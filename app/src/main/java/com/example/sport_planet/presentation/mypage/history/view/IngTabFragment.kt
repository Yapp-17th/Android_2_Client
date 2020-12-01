package com.example.sport_planet.presentation.mypage.history.view

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sport_planet.R
import com.example.sport_planet.data.model.chatting.ChatRoomInfo
import com.example.sport_planet.data.model.mypage.ApplyListModel
import com.example.sport_planet.data.model.mypage.MyViewHistoryModel
import com.example.sport_planet.databinding.FragmentIngTabBinding
import com.example.sport_planet.presentation.base.BaseAcceptCancelDialog
import com.example.sport_planet.presentation.base.BaseFragment
import com.example.sport_planet.presentation.chatting.UserInfo
import com.example.sport_planet.presentation.chatting.view.ChattingActivity
import com.example.sport_planet.presentation.mypage.history.adapter.IngTabAdapter
import com.example.sport_planet.presentation.mypage.history.viewModel.IngTabViewModel

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
    }

    private fun observeLiveData() {
        viewModel.myViewHistoryList.observe(viewLifecycleOwner, Observer {
            binding.run {
                rvHistoryIng.adapter = ingTabAdapter.apply {
                    clEmpty.visibility = View.GONE
                    rvHistoryIng.visibility = View.VISIBLE
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

    private fun showChattingRoomDialog(applyListModel: ApplyListModel) {
        val dialog = BaseAcceptCancelDialog.newInstance(
            dialogTitleText = "",
            dialogBodyText = getString(R.string.dialog_chatting_room_body),
            dialogAcceptText = getString(R.string.dialog_chatting_room_ok),
            dialogWidthRatio = 0.911111f
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