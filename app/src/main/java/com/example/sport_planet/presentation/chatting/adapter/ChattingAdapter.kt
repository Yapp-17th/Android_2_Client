package com.example.sport_planet.presentation.chatting.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.databinding.*
import com.example.sport_planet.model.ChattingMessageResponse
import com.example.sport_planet.model.ChattingRoomListResponse
import com.example.sport_planet.presentation.chatting.ChattingConstant
import com.example.sport_planet.presentation.chatting.ChattingInfo
import com.example.sport_planet.util.Util.formatTo
import java.lang.IllegalArgumentException
import kotlin.collections.ArrayList

class ChattingAdapter(val chatRoomInfo: ChattingRoomListResponse.Data) : RecyclerView.Adapter<ChattingAdapter.Holder>()
{

    private val BOT_MESSAGE_VIEW = 0
    private val NOTICE_MESSAGE_VIEW = 1
    private val RECEIVED_PROFILE_MESSAGE_VIEW = 2
    private val SENT_PROFILE_MESSAGE_VIEW = 3
    private val RECEIVED_TALK_MESSAGE_VIEW = 4
    private val SENT_TALK_MESSAGE_VIEW = 5

    private var chattingMessages = ArrayList<ChattingMessageResponse>()

    fun addChattingMessage(message: ChattingMessageResponse){
        chattingMessages.add(message)
        notifyDataSetChanged()
    }

    /*
    fun settingChattingMessage(message: ArrayList<ChattingMessageResponse>){
        chattingMessages = message
        notifyDataSetChanged()
    }
    */

    inner class Holder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SimpleDateFormat")
        fun bind(chattingMessage: ChattingMessageResponse){
            val messageViewType = itemViewType

            when(messageViewType){

                BOT_MESSAGE_VIEW->{
                    (binding as ItemChatBotMessageBinding).let {
                        it.tvChatBotMessageContent.text = chattingMessage.content
                        it.tvChatBotMessageTimestamp.text = chattingMessage.timestamp.formatTo()
                    }
                }

                NOTICE_MESSAGE_VIEW -> {
                    (binding as ItemNoticeMessageBinding).let {
                        it.tvNoticeMessageContent.text = chattingMessage.content
                    }
                }

                RECEIVED_PROFILE_MESSAGE_VIEW -> {
                    (binding as ItemReceivedProfileMessageBinding).let {
                        it.tvReceivedProfileMessageSenderNickname.text = chattingMessage.senderNickname
                        it.tvReceivedProfileMessageNickname.text = chattingMessage.senderNickname
                        it.tvReceivedProfileMessageIntroduce.text = chattingMessage.content
                        it.tvReceivedProfileMessageTimestamp.text = chattingMessage.timestamp.formatTo()
                    }
                }
                SENT_PROFILE_MESSAGE_VIEW -> {
                    (binding as ItemSentProfileMessageBinding).let {
                        it.tvSentProfileMessageNickname.text = chattingMessage.senderNickname
                        it.tvSentProfileMessageIntroduce.text = chattingMessage.content
                        it.tvSentProfileMessageTimestamp.text = chattingMessage.timestamp.formatTo()
                    }
                }

                RECEIVED_TALK_MESSAGE_VIEW -> {
                    (binding as ItemReceivedTalkMessageBinding).let {
                        it.tvReceivedTalkMessageSenderNickname.text = chattingMessage.senderNickname
                        it.tvReceivedTalkMessageContent.text = chattingMessage.content
                        it.tvReceivedTalkMessageTimestamp.text = chattingMessage.timestamp.formatTo()
                    }
                }
                SENT_TALK_MESSAGE_VIEW -> {
                    (binding as ItemSentTalkMessageBinding).let {
                        it.tvSentTalkMessageContent.text = chattingMessage.content
                        it.tvSentTalkMessageTimestamp.text = chattingMessage.timestamp.formatTo()
                        when(ChattingInfo.USER_ID){
                            chatRoomInfo.hostId -> {
                                if(!chattingMessage.isGuestRead)
                                  it.tvSentTalkMessageIsread.text = "1"
                            }
                            chatRoomInfo.guestId -> {
                                if(!chattingMessage.isHostRead)
                                  it.tvSentTalkMessageIsread.text = "1"
                            }
                        }
                    }
                }

                else -> throw IllegalArgumentException("적절하지 않은 MessageViewType")
            }
        }
    }

    override fun getItemViewType(position: Int) : Int{

        val currentItem: ChattingMessageResponse = chattingMessages[position]
        val messageType = currentItem.type
        val messageSender = currentItem.senderId

        when(messageType){

            ChattingConstant.CHAT_BOT_TYPE -> BOT_MESSAGE_VIEW

            ChattingConstant.PROFILE_TYPE -> {
                return when(messageSender){
                    ChattingInfo.USER_ID -> SENT_PROFILE_MESSAGE_VIEW
                    else -> RECEIVED_PROFILE_MESSAGE_VIEW
                }
            }

            ChattingConstant.TALK_TYPE -> {
                return when(messageSender){
                    ChattingInfo.USER_ID -> SENT_TALK_MESSAGE_VIEW
                    else -> RECEIVED_TALK_MESSAGE_VIEW
                }
            }
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, messageViewType: Int): Holder {

        val messageViewBinding = when(messageViewType){

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

    override fun getItemCount(): Int {
        return chattingMessages.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chattingMessages[position])
    }

    fun RecyclerView.smoothSnapToPosition(position: Int, snapMode: Int = LinearSmoothScroller.SNAP_TO_START) {

        val smoothScroller = object: LinearSmoothScroller(this.context) {
            override fun getVerticalSnapPreference(): Int {
                return snapMode
            }

            override fun getHorizontalSnapPreference(): Int {
                return snapMode
            }
        }
        smoothScroller.targetPosition = position
        layoutManager?.startSmoothScroll(smoothScroller)
    }
}