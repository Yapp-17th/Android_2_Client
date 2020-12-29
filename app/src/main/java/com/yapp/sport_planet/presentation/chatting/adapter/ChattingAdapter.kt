package com.yapp.sport_planet.presentation.chatting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.chatting.ChattingMessageModel
import com.yapp.sport_planet.data.model.chatting.ProfileMessageContentModel
import com.yapp.sport_planet.databinding.*
import com.yapp.sport_planet.presentation.chatting.ChattingConstant
import com.yapp.sport_planet.presentation.chatting.UserInfo
import com.yapp.sport_planet.presentation.custom.CustomNoticeDialog
import com.yapp.sport_planet.presentation.mypage.history.view.HistoryActivity
import com.yapp.sport_planet.presentation.mypage.other.mypage.OtherMyPageActivity
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

class ChattingAdapter(val context: Context) : RecyclerView.Adapter<ChattingAdapter.Holder>() {
    private val BOT_NOTICE_MESSAGE_VIEW = 0
    private val BOT_BOARD_COMPLETE_MESSAGE_VIEW = 1
    private val BOT_MESSAGE_VIEW = 2
    private val RECEIVED_PROFILE_MESSAGE_VIEW = 3
    private val SENT_PROFILE_MESSAGE_VIEW = 4
    private val RECEIVED_TALK_MESSAGE_VIEW = 5
    private val SENT_TALK_MESSAGE_VIEW = 6

    private var chattingMessages = ArrayList<ChattingMessageModel>()

    fun settingChattingMessageList(chattingMessageList: ArrayList<ChattingMessageModel>) {
        chattingMessages = chattingMessageList
        notifyDataSetChanged()
    }

    fun addChattingMessage(chattingMessage: ChattingMessageModel) {
        chattingMessages.add(chattingMessage)
        notifyItemInserted(itemCount)
    }

    fun updateChattingMessage(position: Int, chattingMessage: ChattingMessageModel) {
        chattingMessages[position].isSameTime = chattingMessage.isSameTime
        notifyItemChanged(position)
    }

    inner class Holder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        @OptIn(UnstableDefault::class)
        @SuppressLint("SimpleDateFormat")
        fun bind(chattingMessage: ChattingMessageModel) {

            when (itemViewType) {

                BOT_NOTICE_MESSAGE_VIEW -> {
                    (binding as ItemChatBotNoticeMessageBinding).let {
                        it.itemChatBotNoticeMessage = chattingMessage
                        it.btChatBotNoticeMessageContentDetail.text =
                            context.resources.getString(R.string.item_chat_bot_notice_message_rule)
                        it.btChatBotNoticeMessageContentDetail.setOnClickListener {
                            val dialog = CustomNoticeDialog.CustomNoticeDialogBuilder()
                                .setOnOkClickedListener {}
                                .create()
                            dialog.show(
                                (context as AppCompatActivity).supportFragmentManager,
                                dialog.tag
                            )
                        }
                    }
                }

                BOT_BOARD_COMPLETE_MESSAGE_VIEW -> {
                    (binding as ItemChatBotNoticeMessageBinding).let {
                        it.itemChatBotNoticeMessage = chattingMessage
                        it.btChatBotNoticeMessageContentDetail.text =
                            context.resources.getString(R.string.item_chat_bot_notice_message_history)
                        it.btChatBotNoticeMessageContentDetail.setOnClickListener {
                            val intent = Intent(context, HistoryActivity::class.java)
                            intent.putExtra("tab", 1)
                            ContextCompat.startActivity(context, intent, null)
                        }
                    }
                }

                BOT_MESSAGE_VIEW -> {
                    (binding as ItemChatBotMessageBinding).itemChatBotMessage = chattingMessage
                }

                RECEIVED_PROFILE_MESSAGE_VIEW -> {
                    (binding as ItemReceivedProfileMessageBinding).let {
                        it.itemReceivedProfileMessage = chattingMessage
                        it.profileMessageContent = Json.parse(
                            ProfileMessageContentModel.serializer(),
                            chattingMessage.content
                        )
                        it.btReceivedProfileMessageVisitProfile.setOnClickListener {
                            val intent = Intent(context, OtherMyPageActivity::class.java)
                            intent.putExtra("userId", chattingMessage.senderId)
                            ContextCompat.startActivity(context, intent, null)
                        }
                    }
                }

                SENT_PROFILE_MESSAGE_VIEW -> {
                    (binding as ItemSentProfileMessageBinding).let {
                        it.itemSentProfileMessage = chattingMessage
                        it.profileMessageContent = Json.parse(
                            ProfileMessageContentModel.serializer(),
                            chattingMessage.content
                        )
                    }
                }

                RECEIVED_TALK_MESSAGE_VIEW -> {
                    (binding as ItemReceivedTalkMessageBinding).itemReceivedTalkMessage =
                        chattingMessage
                }

                SENT_TALK_MESSAGE_VIEW -> {
                    (binding as ItemSentTalkMessageBinding).itemSentTalkMessage = chattingMessage
                }

                else -> throw IllegalArgumentException("적절하지 않은 MessageViewType")
            }
        }
    }

    override fun getItemViewType(position: Int): Int {

        val currentItem: ChattingMessageModel = chattingMessages[position]
        val messageType = currentItem.type
        val messageSender = currentItem.senderId

        return when (messageType) {

            ChattingConstant.CHAT_BOT_NOTICE_MESSAGE -> BOT_NOTICE_MESSAGE_VIEW

            ChattingConstant.CHAT_BOT_BOARD_COMPLETE_MESSAGE -> BOT_BOARD_COMPLETE_MESSAGE_VIEW

            ChattingConstant.CHAT_BOT_MESSAGE -> BOT_MESSAGE_VIEW

            ChattingConstant.PROFILE_MESSAGE -> {
                when (messageSender) {
                    UserInfo.USER_ID -> SENT_PROFILE_MESSAGE_VIEW
                    else -> RECEIVED_PROFILE_MESSAGE_VIEW
                }
            }

            ChattingConstant.TALK_MESSAGE -> {
                when (messageSender) {
                    UserInfo.USER_ID -> SENT_TALK_MESSAGE_VIEW
                    else -> RECEIVED_TALK_MESSAGE_VIEW
                }
            }

            else -> throw IllegalArgumentException("적절하지 않은 MessageViewType")

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, messageViewType: Int): Holder {

        val messageViewBinding = when (messageViewType) {

            BOT_NOTICE_MESSAGE_VIEW -> ItemChatBotNoticeMessageBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )

            BOT_BOARD_COMPLETE_MESSAGE_VIEW -> ItemChatBotNoticeMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            BOT_MESSAGE_VIEW -> ItemChatBotMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            RECEIVED_PROFILE_MESSAGE_VIEW -> ItemReceivedProfileMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            SENT_PROFILE_MESSAGE_VIEW -> ItemSentProfileMessageBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )

            RECEIVED_TALK_MESSAGE_VIEW -> ItemReceivedTalkMessageBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )

            SENT_TALK_MESSAGE_VIEW -> ItemSentTalkMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            else -> throw IllegalArgumentException("적절하지 않은 MessageViewType")

        }

        return Holder(messageViewBinding)
    }

    override fun getItemCount(): Int {
        return chattingMessages.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chattingMessages[position])
    }
}