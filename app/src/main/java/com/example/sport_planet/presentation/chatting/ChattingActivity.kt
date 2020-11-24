package com.example.sport_planet.presentation.chatting

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_planet.R
import com.example.sport_planet.data.enums.SeparatorEnum
import com.example.sport_planet.databinding.ActivityChattingBinding
import com.example.sport_planet.model.ChattingMessageResponse
import com.example.sport_planet.model.ChattingRoomListResponse
import com.example.sport_planet.model.enums.ApprovalStatusButtonEnum
import com.example.sport_planet.presentation.base.BaseActivity
import com.example.sport_planet.presentation.chatting.adapter.ChattingAdapter
import com.example.sport_planet.presentation.chatting.viewmodel.ChattingActivityViewModel
import kotlinx.android.synthetic.main.activity_chatting.*
import kotlinx.android.synthetic.main.item_custom_approval_button.*
import kotlinx.android.synthetic.main.item_custom_toolbar.view.*

class ChattingActivity : BaseActivity<ActivityChattingBinding>(R.layout.activity_chatting){

    private lateinit var chatRoomInfo: ChattingRoomListResponse.Data
    private lateinit var chattingAdapter: ChattingAdapter

    private lateinit var layoutChangeListener: View.OnLayoutChangeListener

    private val chattingActivityViewModel: ChattingActivityViewModel
         by lazy {
             ViewModelProvider(this).get(ChattingActivityViewModel::class.java)
         }

    private lateinit var approvalStatusEnum: ApprovalStatusButtonEnum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatRoomInfo = intent.getParcelableExtra("chattingRoomInfo")!!

        chattingAdapter = ChattingAdapter(chatRoomInfo)

        chattingActivityViewModel.initSocket(chatRoomInfo.id)

        layoutChangeListener = View.OnLayoutChangeListener { view, left, top, right, bottom, oldleft, oldtop, oldright, oldbottom->
            if (oldbottom != 0) {
                if (rv_activity_chatting_recyclerview.canScrollVertically(0)) {
                    val pixelsToScrollVertically: Int = oldbottom - bottom
                    rv_activity_chatting_recyclerview.scrollBy(0, pixelsToScrollVertically)
                }
            }
        }

        rv_activity_chatting_recyclerview.addOnLayoutChangeListener(layoutChangeListener)

        this.runOnUiThread {
            binding.toolbarActivityChatting.run {
                if (chatRoomInfo.hostId != UserInfo.USER_ID)
                    this.setSeparator(SeparatorEnum.Host)
                else
                    this.setSeparator(SeparatorEnum.Guest)
                this.setTitle(chatRoomInfo.opponentNickname)
                this.back.setOnClickListener {
                    finish()
                }
            }
            rv_activity_chatting_recyclerview.run {
                adapter = chattingAdapter
                layoutManager = LinearLayoutManager(this@ChattingActivity)
                setHasFixedSize(true)
            }
        }

        chattingActivityViewModel.settingChattingMessageList(chatRoomInfo.id)
        chattingActivityViewModel.chattingMessageListResponseLiveData.observe(this,
            Observer {
                this.runOnUiThread {
                    tv_activity_chatting_board_title.text = it.boardTitle
                    chattingAdapter.settingChattingMessageList(it.data as ArrayList<ChattingMessageResponse>)
                }
                if(it.firstUnreadMessageId == -1)
                    rv_activity_chatting_recyclerview.scrollToPosition(chattingAdapter.itemCount - 1)
                else
                    rv_activity_chatting_recyclerview.scrollToPosition(it.firstUnreadMessageId -1)
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
                approvalStatusEnum = approvalStatus(it)
                bt_activity_chatting_approval_status.setApprovalStatusButton(approvalStatusEnum)
            }
        )

        bt_custom_approval_button.setOnClickListener {
           this.runOnUiThread {
               when(approvalStatusEnum){
                   ApprovalStatusButtonEnum.GUEST_APPLY -> chattingActivityViewModel.applyBoard(chatRoomInfo.boardId, chatRoomInfo.id)
                   ApprovalStatusButtonEnum.HOST_APPROVE -> chattingActivityViewModel.approveBoard(chatRoomInfo.boardId, chatRoomInfo.id, chatRoomInfo.guestId)
                   ApprovalStatusButtonEnum.HOST_APPROVE_CANCLE -> chattingActivityViewModel.disapproveBoard(chatRoomInfo.boardId, chatRoomInfo.id, chatRoomInfo.guestId)
                   else -> bt_custom_approval_button.isEnabled = false
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

    override fun onDestroy() {
        rv_activity_chatting_recyclerview.removeOnLayoutChangeListener(layoutChangeListener)
        chattingActivityViewModel.disconnectSocket()
        super.onDestroy()
    }

    fun approvalStatus(status: String): ApprovalStatusButtonEnum {
        return when(UserInfo.USER_ID){
            chatRoomInfo.guestId -> when(status){
                "PENDING" -> ApprovalStatusButtonEnum.GUEST_APPLY
                "APPLIED"  -> ApprovalStatusButtonEnum.GUEST_APPROVE_AWAIT
                "APPROVED" -> ApprovalStatusButtonEnum.GUEST_APPROVE_SUCCESS
                "DISAPPROVED"  -> ApprovalStatusButtonEnum.GUEST_APPROVE_AWAIT
                else -> throw IllegalArgumentException("적절하지 않은 Guest AppliedStatus")
            }
            chatRoomInfo.hostId  -> when(status){
                "PENDING" -> ApprovalStatusButtonEnum.HOST_NONE
                "APPLIED"  -> ApprovalStatusButtonEnum.HOST_APPROVE
                "APPROVED" -> ApprovalStatusButtonEnum.HOST_APPROVE_CANCLE
                "DISAPPROVED"  -> ApprovalStatusButtonEnum.HOST_APPROVE
                else -> throw IllegalArgumentException("적절하지 않은 Host AppliedStatus")
            }
            else -> throw IllegalArgumentException("적절하지 않은 User Id")
        }
    }
}