package com.yapp.sport_planet.presentation.chatting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.enums.ApprovalStatusButtonEnum
import com.yapp.sport_planet.data.enums.SeparatorEnum
import com.yapp.sport_planet.data.model.chatting.ChatRoomInfo
import com.yapp.sport_planet.data.model.chatting.ChattingMessageModel
import com.yapp.sport_planet.data.response.chatting.ChattingMessageResponse
import com.yapp.sport_planet.databinding.ActivityChattingBinding
import com.yapp.sport_planet.presentation.base.BaseActivity
import com.yapp.sport_planet.presentation.board.BoardActivity
import com.yapp.sport_planet.presentation.chatting.ChattingConstant
import com.yapp.sport_planet.presentation.chatting.adapter.ChattingAdapter
import com.yapp.sport_planet.presentation.chatting.viewmodel.ChattingActivityViewModel
import com.yapp.sport_planet.presentation.custom.CustomDialog
import com.yapp.sport_planet.util.Util
import kotlinx.android.synthetic.main.activity_chatting.*
import kotlinx.android.synthetic.main.item_custom_approval_button.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class ChattingActivity : BaseActivity<ActivityChattingBinding>(R.layout.activity_chatting){

    private lateinit var chatRoomInfo: ChatRoomInfo

    private lateinit var chattingAdapter: ChattingAdapter

    private lateinit var chattingActivityViewModel: ChattingActivityViewModel

    private var isPageFilledWithItems by Delegates.notNull<Boolean>()

    private lateinit var layoutChangeListener: View.OnLayoutChangeListener

    private var pixelsToScrollVertically by Delegates.notNull<Int>()

    private var chattingMessages = ArrayList<ChattingMessageModel>()

    private lateinit var priorDate: String
    private lateinit var thisDate: String
    private var isSameDate by Delegates.notNull<Boolean>()

    private lateinit var priorTime: String
    private lateinit var thisTime: String
    private var isSameTime by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatRoomInfo = intent.getParcelableExtra("chatRoomInfo")!!

        chattingActivityViewModel =
            ViewModelProvider(this, ChattingActivityViewModel.ViewModelFactory(chatRoomInfo))
                .get(ChattingActivityViewModel::class.java)

        chattingAdapter = ChattingAdapter(this)

        chattingActivityViewModel.initSocket()

        this.runOnUiThread {
            binding.toolbarActivityChatting.run {
                if (!chatRoomInfo.isHost)
                    this.setSeparator(SeparatorEnum.Host)
                else
                    this.setSeparator(SeparatorEnum.Guest)
                this.setTitle(chatRoomInfo.opponentNickname)
                this.setBackButtonClick(View.OnClickListener { finish() })
            }
        }

        val mLayoutManager = object : LinearLayoutManager(applicationContext) {
            override fun onLayoutCompleted(state: RecyclerView.State) {
                super.onLayoutCompleted(state)
                val childCount = childCount
                val itemCount = itemCount
                isPageFilledWithItems = childCount < itemCount
            }
        }

        rv_activity_chatting_recyclerview.run {
            adapter = chattingAdapter
            layoutManager = mLayoutManager
            setHasFixedSize(true)
        }

        chattingActivityViewModel.settingChattingMessageList(chatRoomInfo.chatRoomId)
        chattingActivityViewModel.chattingMessageListResponseLiveData.observe(this,
            Observer {
                tv_activity_chatting_board_title.text = it.boardTitle

                for(chattingMessage in it.data) {
                    chattingMessageFactory(chattingMessage, false)
                    chattingMessages.add(
                        ChattingMessageModel(
                            chattingMessage.content,
                            chattingMessage.type,
                            chattingMessage.messageId,
                            chattingMessage.senderId,
                            chattingMessage.senderNickname,
                            thisDate,
                            thisTime,
                            isSameDate,
                            isSameTime
                        )
                    )
                }

                chattingAdapter.settingChattingMessageList(chattingMessages)

                rv_activity_chatting_recyclerview.postDelayed({
                    (rv_activity_chatting_recyclerview.layoutManager as LinearLayoutManager).stackFromEnd = isPageFilledWithItems
                    if (it.firstUnreadMessageId != -1)
                        (rv_activity_chatting_recyclerview.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(it.firstUnreadMessageId, 0)
                    if(!isPageFilledWithItems)
                        rv_activity_chatting_recyclerview.addOnLayoutChangeListener(layoutChangeListener)
                    rv_activity_chatting_recyclerview.postDelayed({
                        view_activity_chatting_loading.visibility = View.GONE
                    },100)
                },50)
            }
        )

        layoutChangeListener = View.OnLayoutChangeListener{ view, left, top, right, bottom, oldleft, oldtop, oldright, oldbottom ->

            pixelsToScrollVertically = oldbottom - bottom
            rv_activity_chatting_recyclerview.scrollBy(0, pixelsToScrollVertically)

            if(pixelsToScrollVertically < 0) {
                isPageFilledWithItems = rv_activity_chatting_recyclerview.computeVerticalScrollRange() > rv_activity_chatting_recyclerview.height;
                (rv_activity_chatting_recyclerview.layoutManager as LinearLayoutManager).stackFromEnd = isPageFilledWithItems
                if(isPageFilledWithItems){
                    rv_activity_chatting_recyclerview.removeOnLayoutChangeListener(layoutChangeListener)
                }
            }
        }

        chattingActivityViewModel.noticeMessageLiveData.observe(this,
            Observer {
                thisDate = Util.toDateFormat(it.createdAt)
                thisTime = Util.toTimeFormat(it.createdAt)

                if(it.messageId > 0) {
                    priorDate = chattingMessages[it.messageId.toInt() - 1].createdDate
                    isSameDate = priorDate == thisDate
                }
                else{
                    isSameDate = false
                }

                chattingAdapter.addChattingMessage(
                    ChattingMessageModel(
                        it.content,
                        it.type,
                        it.messageId,
                        it.senderId,
                        it.senderNickname,
                        thisDate,
                        thisTime,
                        isSameDate,
                        ChattingConstant.IS_NOT_SAME_TIME_MESSAGE
                    )
                )
                rv_activity_chatting_recyclerview.smoothScrollToPosition(chattingAdapter.itemCount -1)
            }
        )

        chattingActivityViewModel.chattingMessageLiveData.observe(this,
            Observer {

                chattingMessageFactory(it, true)
                chattingAdapter.addChattingMessage(
                    ChattingMessageModel(
                        it.content,
                        it.type,
                        it.messageId,
                        it.senderId,
                        it.senderNickname,
                        thisDate,
                        thisTime,
                        isSameDate,
                        isSameTime
                    )
                )
                rv_activity_chatting_recyclerview.smoothScrollToPosition(chattingAdapter.itemCount -1)
            }
        )

        chattingActivityViewModel.approvalStatusLiveData.observe(this,
            Observer {
                bt_activity_chatting_approval_status.setApprovalStatusButton(it)
            }
        )

        chattingActivityViewModel.showErrorToastLiveData.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, R.string.activity_chatting_toast, Toast.LENGTH_SHORT).show()
            }
        })

        bt_activity_chatting_visit_original_board.setOnClickListener {
            BoardActivity.createInstance(this, chatRoomInfo.boardId)
        }

        bt_custom_approval_button.setOnClickListener {
            when(chattingActivityViewModel.approvalStatusLiveData.value){
                ApprovalStatusButtonEnum.GUEST_APPLY -> {
                    val dialog = CustomDialog.CustomDialogBuilder()
                        .setContent(getString(R.string.dialog_common_content1))
                        .setOKText(getString(R.string.dialog_common_ok1))
                        .setOnOkClickedListener{
                            chattingActivityViewModel.approvalStatusButtonOnClick()
                        }.create()
                    dialog.show(supportFragmentManager, dialog.tag)
                }
                else -> chattingActivityViewModel.approvalStatusButtonOnClick()
            }
        }

        bt_activity_chatting_send.setOnClickListener{
            if(et_activity_chatting_message_content.length() > 0) {
                chattingActivityViewModel.sendMessage(et_activity_chatting_message_content.text.toString())
                et_activity_chatting_message_content.text.clear()
            }
        }

    }

    override fun onStop() {
        chattingActivityViewModel.sendReadUpdateMessage()
        ChattingFragment.currentChattingRoomNum = -1L
        super.onStop()
    }

    override fun onDestroy() {
        chattingActivityViewModel.disconnectSocket()
        rv_activity_chatting_recyclerview.removeOnLayoutChangeListener(layoutChangeListener)
        super.onDestroy()
    }

    private fun chattingMessageFactory(chattingMessage: ChattingMessageResponse, update: Boolean){
        thisDate = Util.toDateFormat(chattingMessage.createdAt)
        thisTime = Util.toTimeFormat(chattingMessage.createdAt)

        if(chattingMessage.messageId > 0) {
            val priorMessageId = chattingMessage.messageId.toInt() - 1

            priorDate = chattingMessages[priorMessageId].createdDate
            isSameDate = priorDate == thisDate

            priorTime = chattingMessages[priorMessageId].createdTime

            if(thisTime == priorTime && chattingMessage.senderId == chattingMessages[priorMessageId].senderId && chattingMessage.type == chattingMessages[priorMessageId].type){
                when(chattingMessages[priorMessageId].isSameTime){
                    ChattingConstant.IS_NOT_SAME_TIME_MESSAGE -> {
                        chattingMessages[priorMessageId].isSameTime = ChattingConstant.IS_SAME_TIME_HEADER_MESSAGE
                        if(update)
                            chattingAdapter.updateChattingMessage(priorMessageId, chattingMessages[priorMessageId])
                        isSameTime = ChattingConstant.IS_SAME_TIME_FOOTER_MESSAGE
                    }
                    ChattingConstant.IS_SAME_TIME_FOOTER_MESSAGE -> {
                        chattingMessages[priorMessageId].isSameTime = ChattingConstant.IS_SAME_TIME_BODY_MESSAGE
                        if(update)
                            chattingAdapter.updateChattingMessage(priorMessageId, chattingMessages[priorMessageId])
                        isSameTime = ChattingConstant.IS_SAME_TIME_FOOTER_MESSAGE
                    }
                }
            }
            else{
                isSameTime = ChattingConstant.IS_NOT_SAME_TIME_MESSAGE
            }
        }
        else{
            isSameDate = false
            isSameTime = ChattingConstant.IS_NOT_SAME_TIME_MESSAGE
        }
    }
}