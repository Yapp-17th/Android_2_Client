package com.example.sport_planet.presentation.chatting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.data.model.chatting.ChattingMessageModel
import com.example.sport_planet.data.model.chatting.ProfileMessageContentModel
import com.example.sport_planet.databinding.*
import com.example.sport_planet.presentation.chatting.ChattingConstant
import com.example.sport_planet.presentation.chatting.UserInfo
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

class ChattingAdapter : RecyclerView.Adapter<ChattingAdapter.Holder>()
{
    private val BOT_NOTICE_MESSAGE_VIEW = 0
    private val BOT_MESSAGE_VIEW = 1
    private val RECEIVED_PROFILE_MESSAGE_VIEW = 2
    private val SENT_PROFILE_MESSAGE_VIEW = 3
    private val RECEIVED_TALK_MESSAGE_VIEW = 4
    private val SENT_TALK_MESSAGE_VIEW = 5

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

        @OptIn(UnstableDefault::class)
        @SuppressLint("SimpleDateFormat")
        fun bind(chattingMessage: ChattingMessageModel){

            when(itemViewType){

                BOT_NOTICE_MESSAGE_VIEW -> {
                    (binding as ItemChatBotNoticeMessageBinding).itemChatBotNoticeMessage = chattingMessage
                }

                BOT_MESSAGE_VIEW ->{
                    (binding as ItemChatBotMessageBinding).itemChatBotMessage = chattingMessage
                }

                RECEIVED_PROFILE_MESSAGE_VIEW -> {
                    (binding as ItemReceivedProfileMessageBinding).let {
                        it.itemReceivedProfileMessage = chattingMessage
                        it.profileMessageContent = Json.parse(ProfileMessageContentModel.serializer(), chattingMessage.content)
                    }
                }

                SENT_PROFILE_MESSAGE_VIEW -> {
                    (binding as ItemSentProfileMessageBinding).let {
                        it.itemSentProfileMessage = chattingMessage
                        it.profileMessageContent = Json.parse(ProfileMessageContentModel.serializer(), chattingMessage.content)
                    }
                }

                RECEIVED_TALK_MESSAGE_VIEW -> {
                    (binding as ItemReceivedTalkMessageBinding).itemReceivedTalkMessage = chattingMessage
                }

                SENT_TALK_MESSAGE_VIEW -> {
                    (binding as ItemSentTalkMessageBinding).itemSentTalkMessage = chattingMessage
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