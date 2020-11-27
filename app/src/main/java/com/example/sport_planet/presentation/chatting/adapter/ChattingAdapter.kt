package com.example.sport_planet.presentation.chatting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.data.model.chatting.ChattingMessageModel
import com.example.sport_planet.databinding.*
import com.example.sport_planet.presentation.chatting.ChattingConstant
import com.example.sport_planet.presentation.chatting.UserInfo

class ChattingAdapter : RecyclerView.Adapter<ChattingAdapter.Holder>()
{
    private val BOT_NOTICE_MESSAGE_VIEW = 0
    private val BOT_MESSAGE_VIEW = 1
    private val NOTICE_MESSAGE_VIEW = 2
    private val RECEIVED_PROFILE_MESSAGE_VIEW = 3
    private val SENT_PROFILE_MESSAGE_VIEW = 4
    private val RECEIVED_TALK_MESSAGE_VIEW = 5
    private val SENT_TALK_MESSAGE_VIEW = 6

    private val IS_NOT_SAME_TIME_MESSAGE = 0
    private val IS_SAME_TIME_HEADER_MESSAGE = 1
    private val IS_SAME_TIME_BODY_MESSAGE = 2
    private val IS_SAME_TIME_FOOTER_MESSAGE = 3

    private var chattingMessages = ArrayList<ChattingMessageModel>()

    fun settingChattingMessageList(chattingMessageList: ArrayList<ChattingMessageModel>){
        chattingMessages = chattingMessageList
        notifyDataSetChanged()
    }

    fun addChattingMessage(chattingMessage: ChattingMessageModel){
        chattingMessages.add(chattingMessage)
        notifyItemInserted(itemCount)
    }

    fun updateChattingMessage(position: Int, chattingMessage: ChattingMessageModel){
        chattingMessages[position].isSameTime = chattingMessage.isSameTime
        notifyItemChanged(position)
    }

    inner class Holder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SimpleDateFormat")
        fun bind(chattingMessage: ChattingMessageModel){

            when(itemViewType){

                BOT_NOTICE_MESSAGE_VIEW -> {
                    (binding as ItemChatBotNoticeMessageBinding).let {
                        it.itemChatBotNoticeMessage = chattingMessage

                        if (!chattingMessage.isSameDate) {
                            it.tvNoticeMessageContent.visibility = View.VISIBLE
                            it.tvNoticeMessageContent.text = chattingMessage.createdDate
                        } else
                            it.tvNoticeMessageContent.visibility = View.GONE
                    }
                }

                BOT_MESSAGE_VIEW ->{
                    (binding as ItemChatBotMessageBinding).let {
                        it.itemChatBotMessage = chattingMessage

                        if (!chattingMessage.isSameDate) {
                            it.tvNoticeMessageContent.visibility = View.VISIBLE
                            it.tvNoticeMessageContent.text = chattingMessage.createdDate
                        } else
                            it.tvNoticeMessageContent.visibility = View.GONE
                    }
                }

                NOTICE_MESSAGE_VIEW -> {
                    (binding as ItemNoticeMessageBinding).let {
                        it.itemNoticeMessage = chattingMessage

                        if (!chattingMessage.isSameDate) {
                            it.tvNoticeMessageContent.visibility = View.VISIBLE
                            it.tvNoticeMessageContent.text = chattingMessage.createdDate
                        } else
                            it.tvNoticeMessageContent.visibility = View.GONE
                    }
                }

                RECEIVED_PROFILE_MESSAGE_VIEW -> {
                    (binding as ItemReceivedProfileMessageBinding).let {
                        it.itemReceivedProfileMessage = chattingMessage

                        if (!chattingMessage.isSameDate) {
                            it.tvNoticeMessageContent.visibility = View.VISIBLE
                            it.tvNoticeMessageContent.text = chattingMessage.createdDate
                        } else
                            it.tvNoticeMessageContent.visibility = View.GONE
                    }
                }

                SENT_PROFILE_MESSAGE_VIEW -> {
                    (binding as ItemSentProfileMessageBinding).let {
                        it.itemSentProfileMessage = chattingMessage

                        if (!chattingMessage.isSameDate) {
                            it.tvNoticeMessageContent.visibility = View.VISIBLE
                            it.tvNoticeMessageContent.text = chattingMessage.createdDate
                        } else
                            it.tvNoticeMessageContent.visibility = View.GONE
                    }
                }

                RECEIVED_TALK_MESSAGE_VIEW -> {
                    (binding as ItemReceivedTalkMessageBinding).let {
                        it.itemReceivedTalkMessage = chattingMessage

                        if (!chattingMessage.isSameDate) {
                            it.tvNoticeMessageContent.visibility = View.VISIBLE
                            it.tvNoticeMessageContent.text = chattingMessage.createdDate
                        } else
                            it.tvNoticeMessageContent.visibility = View.GONE

                        when(chattingMessage.isSameTime){
                            IS_NOT_SAME_TIME_MESSAGE -> {
                                it.ivReceivedTalkMessageProfileImg.visibility = View.VISIBLE
                                it.tvReceivedTalkMessageSenderNickname.visibility = View.VISIBLE
                                it.tvReceivedTalkMessageTimestamp.visibility = View.VISIBLE
                            }

                            IS_SAME_TIME_HEADER_MESSAGE -> {
                                it.ivReceivedTalkMessageProfileImg.visibility = View.VISIBLE
                                it.tvReceivedTalkMessageSenderNickname.visibility = View.VISIBLE
                                it.tvReceivedTalkMessageTimestamp.visibility = View.GONE
                                it.viewItemReceivedTalkMessageMargin.visibility = View.VISIBLE
                            }

                            IS_SAME_TIME_BODY_MESSAGE -> {
                                it.ivReceivedTalkMessageProfileImg.visibility = View.GONE
                                it.tvReceivedTalkMessageSenderNickname.visibility = View.GONE
                                it.tvReceivedTalkMessageTimestamp.visibility = View.GONE
                                it.viewItemReceivedTalkMessageMargin.visibility = View.GONE
                            }

                            IS_SAME_TIME_FOOTER_MESSAGE -> {
                                it.ivReceivedTalkMessageProfileImg.visibility = View.GONE
                                it.tvReceivedTalkMessageSenderNickname.visibility = View.GONE
                                it.tvReceivedTalkMessageTimestamp.visibility = View.VISIBLE
                                it.viewItemReceivedTalkMessageMargin.visibility = View.GONE
                            }
                        }
                    }
                }

                SENT_TALK_MESSAGE_VIEW -> {
                    (binding as ItemSentTalkMessageBinding).let {
                        it.itemSentTalkMessage = chattingMessage

                        if (!chattingMessage.isSameDate) {
                            it.tvNoticeMessageContent.visibility = View.VISIBLE
                            it.tvNoticeMessageContent.text = chattingMessage.createdDate
                        } else
                            it.tvNoticeMessageContent.visibility = View.GONE

                        when(chattingMessage.isSameTime){
                            IS_NOT_SAME_TIME_MESSAGE -> {
                                it.tvSentTalkMessageTimestamp.visibility = View.VISIBLE
                                it.viewItemReceivedTalkMessageMargin.visibility = View.VISIBLE
                            }

                            IS_SAME_TIME_HEADER_MESSAGE -> {
                                it.tvSentTalkMessageTimestamp.visibility = View.GONE
                                it.viewItemReceivedTalkMessageMargin.visibility = View.VISIBLE
                            }

                            IS_SAME_TIME_BODY_MESSAGE -> {
                                it.tvSentTalkMessageTimestamp.visibility = View.GONE
                                it.viewItemReceivedTalkMessageMargin.visibility = View.GONE
                            }

                            IS_SAME_TIME_FOOTER_MESSAGE -> {
                                it.tvSentTalkMessageTimestamp.visibility = View.VISIBLE
                                it.viewItemReceivedTalkMessageMargin.visibility = View.GONE
                            }
                        }
                    }
                }

                else -> throw IllegalArgumentException("적절하지 않은 MessageViewType")
            }
        }
    }

    override fun getItemViewType(position: Int) : Int{

        val currentItem: ChattingMessageModel = chattingMessages[position]
        val messageType = currentItem.type
        val messageSender = currentItem.senderId

        return when(messageType){

            ChattingConstant.CHAT_BOT_NOTICE_MESSAGE -> BOT_NOTICE_MESSAGE_VIEW
            ChattingConstant.CHAT_BOT_MESSAGE -> BOT_MESSAGE_VIEW

            ChattingConstant.PROFILE_MESSAGE -> {
                when(messageSender){
                    UserInfo.USER_ID -> SENT_PROFILE_MESSAGE_VIEW
                    else -> RECEIVED_PROFILE_MESSAGE_VIEW
                }
            }

            ChattingConstant.TALK_MESSAGE -> {
                when(messageSender){
                    UserInfo.USER_ID -> SENT_TALK_MESSAGE_VIEW
                    else -> RECEIVED_TALK_MESSAGE_VIEW
                }
            }
            else -> throw IllegalArgumentException("적절하지 않은 MessageViewType")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, messageViewType: Int): Holder {

        val messageViewBinding = when(messageViewType){

            BOT_NOTICE_MESSAGE_VIEW -> ItemChatBotNoticeMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            BOT_MESSAGE_VIEW -> ItemChatBotMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            NOTICE_MESSAGE_VIEW -> ItemNoticeMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            RECEIVED_PROFILE_MESSAGE_VIEW -> ItemReceivedProfileMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            SENT_PROFILE_MESSAGE_VIEW -> ItemSentProfileMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            RECEIVED_TALK_MESSAGE_VIEW -> ItemReceivedTalkMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            SENT_TALK_MESSAGE_VIEW -> ItemSentTalkMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            else -> throw IllegalArgumentException("적절하지 않은 MessageViewType")
        }

        return Holder(messageViewBinding)
    }

    override fun getItemId(position: Int): Long {
        return chattingMessages[position].messageId!!
    }

    override fun getItemCount(): Int {
        return chattingMessages.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chattingMessages[position])
    }

}