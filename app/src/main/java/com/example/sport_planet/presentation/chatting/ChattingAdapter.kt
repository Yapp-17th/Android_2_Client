package com.example.sport_planet.presentation.chatting

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.presentation.chatting.model.ChattingMessage
import java.lang.IllegalArgumentException

class ChattingAdapter(val context: Context) : RecyclerView.Adapter<ChattingAdapter.Holder>()
{
    val chattingInfo = ChattingInfo

    val SENT_VIEW = 1 // 발신 메세지
    val RECEIVED_VIEW = 2 // 수신 메세지

    var chattingMessages = ArrayList<ChattingMessage>()

    fun addChattingMessage(message: ChattingMessage){
        chattingMessages.add(message)
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){

        val receivedTalkMessageContent = itemView.findViewById<TextView>(R.id.tv_received_talk_message_content)
        val sentTalkMessageContent = itemView.findViewById<TextView>(R.id.tv_sent_talk_message_content)

        fun bind(chattingMessage: ChattingMessage, context: Context){
            val viewType = itemViewType

            when(viewType){
                RECEIVED_VIEW -> {
                    receivedTalkMessageContent.text = chattingMessage.content
                }
                SENT_VIEW -> {
                    sentTalkMessageContent.text = chattingMessage.content
                }
                else -> throw IllegalArgumentException("적절하지 않은 viewType")
            }
        }
    }

    override fun getItemViewType(position: Int) : Int{
        val currentItem: ChattingMessage = chattingMessages[position]
        val messageType = currentItem.type
        val messageSender = currentItem.sender

        when(messageType){

            chattingInfo.MESSAGE_TYPE_TALK -> {
                return when(messageSender){
                    chattingInfo.SENDER -> SENT_VIEW
                    else -> RECEIVED_VIEW
                }
            }
        }

        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val view = when(viewType){
            RECEIVED_VIEW -> LayoutInflater.from(context).inflate(R.layout.item_received_talk_message, parent, false)
            SENT_VIEW -> LayoutInflater.from(context).inflate(R.layout.item_sent_talk_message, parent, false)
            else -> throw IllegalArgumentException("적절하지 않은 viewType")
        }

        return Holder(view)
    }

    override fun getItemCount(): Int {
        return chattingMessages.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chattingMessages[position], context)
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