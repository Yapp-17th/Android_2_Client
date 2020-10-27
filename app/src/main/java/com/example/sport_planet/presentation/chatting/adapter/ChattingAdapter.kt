package com.example.sport_planet.presentation.chatting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.databinding.*
import com.example.sport_planet.presentation.chatting.ChattingInfo
import com.example.sport_planet.presentation.chatting.model.ChattingMessage
import java.lang.IllegalArgumentException


class ChattingAdapter : RecyclerView.Adapter<ChattingAdapter.Holder>()
{
    private val chattingInfo = ChattingInfo

    private val NOTICE_MESSAGE_VIEW = 0
    private val RECEIVED_PROFILE_MESSAGE_VIEW = 1
    private val SENT_PROFILE_MESSAGE_VIEW = 2
    private val RECEIVED_TALK_MESSAGE_VIEW = 3
    private val SENT_TALK_MESSAGE_VIEW = 4

    private var chattingMessages = ArrayList<ChattingMessage>()

    fun addChattingMessage(message: ChattingMessage){
        chattingMessages.add(message)
        notifyDataSetChanged()
    }

    inner class Holder(val binding: ViewDataBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(chattingMessage: ChattingMessage){
            val messageViewType = itemViewType

            when(messageViewType){

                NOTICE_MESSAGE_VIEW -> {
                    (binding as ItemNoticeMessageBinding).let {
                        it.tvNoticeMessageContent.text = chattingMessage.content
                    }
                }

                RECEIVED_PROFILE_MESSAGE_VIEW -> {

                }
                SENT_PROFILE_MESSAGE_VIEW -> {

                }

                RECEIVED_TALK_MESSAGE_VIEW -> {
                    (binding as ItemReceivedTalkMessageBinding).let {
                        it.tvReceivedTalkMessageContent.text = chattingMessage.content
                        it.tvReceivedTalkMessageTimestamp.text = chattingMessage.timestamp
                    }
                }
                SENT_TALK_MESSAGE_VIEW -> {
                    (binding as ItemSentTalkMessageBinding).let {
                        it.tvSentTalkMessageContent.text = chattingMessage.content
                        it.tvSentTalkMessageTimestamp.text = chattingMessage.timestamp
                    }
                }

                else -> throw IllegalArgumentException("적절하지 않은 MessageViewType")
            }
        }
    }

    override fun getItemViewType(position: Int) : Int{

        val currentItem: ChattingMessage = chattingMessages[position]
        val messageType = currentItem.type
        val messageSender = currentItem.senderId

        when(messageType){

            ChattingInfo.MESSAGE_TYPE_TALK -> {
                return when(messageSender){
                    ChattingInfo.SENDER_ID -> SENT_TALK_MESSAGE_VIEW
                    else -> RECEIVED_TALK_MESSAGE_VIEW
                }
            }
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, messageViewType: Int): Holder {

        val messageViewBinding = when(messageViewType){

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