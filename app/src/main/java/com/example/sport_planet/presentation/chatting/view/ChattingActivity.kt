package com.example.sport_planet.presentation.chatting.view

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.SeparatorEnum
import com.example.sport_planet.databinding.ActivityChattingBinding
import com.example.sport_planet.data.response.ChattingMessageResponse
import com.example.sport_planet.data.enums.ApprovalStatusButtonEnum
import com.example.sport_planet.data.model.ChatRoomInfo
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.chatting.adapter.ChattingAdapter
import com.example.sport_planet.presentation.chatting.viewmodel.ChattingActivityViewModel
import kotlinx.android.synthetic.main.activity_chatting.*
import kotlinx.android.synthetic.main.item_custom_approval_button.*
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class ChattingActivity : BaseActivity<ActivityChattingBinding>(R.layout.activity_chatting){

    private lateinit var chatRoomInfo: ChatRoomInfo

    private lateinit var chattingAdapter: ChattingAdapter

    private val chattingActivityViewModel: ChattingActivityViewModel
         by lazy {
             ViewModelProvider(this).get(ChattingActivityViewModel::class.java)
         }

    private lateinit var approvalStatusEnum: ApprovalStatusButtonEnum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatRoomInfo = intent.getParcelableExtra("chatRoomInfo")!!

        chattingAdapter = ChattingAdapter()
        chattingAdapter.setHasStableIds(true)

        chattingActivityViewModel.initSocket(chatRoomInfo.chatRoomId)

        this.runOnUiThread {
            binding.toolbarActivityChatting.run {
                if (!chatRoomInfo.isHost)
                    this.setSeparator(SeparatorEnum.Host)
                else
                    this.setSeparator(SeparatorEnum.Guest)
                this.setTitle(chatRoomInfo.opponentNickname)
                this.back.setOnClickListener {
                    finish()
                }
            }

            val linearLayoutManager = LinearLayoutManager(this@ChattingActivity)
            linearLayoutManager.stackFromEnd = true

            rv_activity_chatting_recyclerview.run {
                adapter = chattingAdapter
                layoutManager = linearLayoutManager
                setHasFixedSize(true)
            }
        }

        chattingActivityViewModel.settingChattingMessageList(chatRoomInfo.chatRoomId)
        chattingActivityViewModel.chattingMessageListResponseLiveData.observe(this,
            Observer {
                this.runOnUiThread {
                    tv_activity_chatting_board_title.text = it.boardTitle
                    chattingAdapter.settingChattingMessageList(it.data as ArrayList<ChattingMessageResponse>)
                }
                if (it.firstUnreadMessageId != -1)
                    rv_activity_chatting_recyclerview.scrollToPosition(it.firstUnreadMessageId)
            }
        )

        chattingActivityViewModel.chattingMessageLiveData.observe(this,
            Observer {
                this.runOnUiThread {
                    chattingAdapter.addChattingMessage(it)
                    rv_activity_chatting_recyclerview.smoothScrollToPosition(chattingAdapter.itemCount -1)
                }
            }
        )

        chattingActivityViewModel.approvalStatusLiveData.observe(this,
            Observer {
                approvalStatusEnum = bt_activity_chatting_approval_status.approvalStatus(chatRoomInfo.isHost, it)
                bt_activity_chatting_approval_status.setApprovalStatusButton(approvalStatusEnum)
            }
        )

        bt_custom_approval_button.setOnClickListener {
            when(approvalStatusEnum){
                ApprovalStatusButtonEnum.GUEST_APPLY -> chattingActivityViewModel.applyBoard(chatRoomInfo.boardId, chatRoomInfo.chatRoomId)
                ApprovalStatusButtonEnum.HOST_APPROVE -> chattingActivityViewModel.approveBoard(chatRoomInfo.boardId, chatRoomInfo.chatRoomId, chatRoomInfo.guestId)
                ApprovalStatusButtonEnum.HOST_APPROVE_CANCLE -> chattingActivityViewModel.disapproveBoard(chatRoomInfo.boardId, chatRoomInfo.chatRoomId, chatRoomInfo.guestId)
                else -> bt_custom_approval_button.isEnabled = false
            }
        }

        bt_activity_chatting_send.setOnClickListener{
            if(et_activity_chatting_message_content.length() > 0) {
                chattingActivityViewModel.sendMessage(et_activity_chatting_message_content.text.toString())
                et_activity_chatting_message_content.text.clear()
            }
        }

    }

    override fun onDestroy() {
        chattingActivityViewModel.disconnectSocket()
        super.onDestroy()
    }
}