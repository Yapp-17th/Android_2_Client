package com.example.sport_planet.presentation.chatting

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ActivityChattingBinding
import com.example.sport_planet.model.ChattingRoomListResponse
import com.example.sport_planet.model.enums.SeparatorEnum
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.chatting.adapter.ChattingAdapter
import com.example.sport_planet.presentation.chatting.viewmodel.ChattingActivityViewModel
import kotlinx.android.synthetic.main.activity_chatting.*
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class ChattingActivity : BaseActivity<ActivityChattingBinding>(R.layout.activity_chatting) {

    private val chattingAdapter = ChattingAdapter()
    private val chattingActivityViewModel: ChattingActivityViewModel
         by lazy {
             ViewModelProvider(this).get(ChattingActivityViewModel::class.java)
         }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val chatRoomInfo = intent.getParcelableExtra<ChattingRoomListResponse.Data>("chattingRoomInfo")

        if (chatRoomInfo != null) {
            ChattingInfo.settingChattingInfo(chatRoomInfo.id)
        }

        this.runOnUiThread {
            binding.toolbarActivityChatting.run {
                if (chatRoomInfo != null) {
                    if (chatRoomInfo.hostId != ChattingInfo.USER_ID)
                        this.setSeparator(SeparatorEnum.HOST)
                    else
                        this.setSeparator(SeparatorEnum.GUEST)
                    this.title.text = chatRoomInfo.opponentNickname
                }
            }
        }

        rv_activity_chatting_recyclerview.run {
            adapter = chattingAdapter
            layoutManager = LinearLayoutManager(this@ChattingActivity)
            setHasFixedSize(true)
        }

        chattingActivityViewModel.settingChattingMessageList(ChattingInfo.CHATROOM_ID)
        chattingActivityViewModel.ChattingMessageListResponseLiveData.observe(this,
            Observer {
                tv_activity_chatting_board_title.text = it.boardTitle
                for(chattingMessage in it.data){
                    chattingAdapter.addChattingMessage(chattingMessage)
                    if(it.firstUnreadMessageId == -1)
                        rv_activity_chatting_recyclerview.smoothScrollToPosition(chattingAdapter.itemCount)
                    else
                        rv_activity_chatting_recyclerview.smoothScrollToPosition(it.firstUnreadMessageId)
                }
            }
        )

        chattingActivityViewModel.settingStomp()
        chattingActivityViewModel.ChattingMessageLiveData.observe(this,
                Observer {
                    chattingAdapter.addChattingMessage(it)
                    rv_activity_chatting_recyclerview.smoothScrollToPosition(chattingAdapter.itemCount)
                }
        )

        bt_activity_chatting_approval_status.setOnClickListener {
            if (chatRoomInfo != null) {
                when(ChattingInfo.USER_ID){
                    chatRoomInfo.guestId -> chattingActivityViewModel.applyBoard(chatRoomInfo.boardId, chatRoomInfo.id)
                    chatRoomInfo.hostId -> chattingActivityViewModel.approveBoard(chatRoomInfo.boardId, chatRoomInfo.id, chatRoomInfo.guestId)
                }
            }
        }

        bt_activity_chatting_send.setOnClickListener{
            if(et_activity_chatting_message_content.length() > 0) {
                chattingActivityViewModel.sendMessage(et_activity_chatting_message_content.text.toString())
                et_activity_chatting_message_content.text.clear()
            }
        }

    }
}