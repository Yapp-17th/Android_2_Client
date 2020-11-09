package com.example.sport_planet.presentation.chatting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ItemChattingRoomBinding
import com.example.sport_planet.model.ChattingRoomListResponse
import com.example.sport_planet.presentation.chatting.ChattingActivity
import com.example.sport_planet.presentation.chatting.ChattingInfo
import com.example.sport_planet.util.Util.formatTo
import kotlin.collections.ArrayList

class ChattingRoomAdapter(val context: Context) : RecyclerView.Adapter<ChattingRoomAdapter.Holder>() {

    private var chattingRooms = ArrayList<ChattingRoomListResponse.Data>()

    fun settingChattingRoomList(chattingRoomList: ArrayList<ChattingRoomListResponse.Data>){
        chattingRooms = chattingRoomList
        chattingRooms.sortByDescending { chattingRoom -> chattingRoom.lastMessage.timestamp }
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemChattingRoomBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(chattingRoom: ChattingRoomListResponse.Data){

            if (chattingRoom.unreadMessages == 0)
                binding.ivChattingRoomUnreadMessage.visibility = View.INVISIBLE

            if(chattingRoom.hostId != ChattingInfo.USER_ID ){
                binding.tvChattingRoomPosition.text = "Host"
                binding.tvChattingRoomPosition.setTextColor(ContextCompat.getColor(context, R.color.pink))
            }

            binding.tvChattingRoomNickname.text = chattingRoom.opponentNickname
            binding.tvChattingRoomLastMessageTimestamp.text = chattingRoom.lastMessage.timestamp.formatTo()
            binding.tvChattingRoomLastMessageContent.text = chattingRoom.lastMessage.content
            binding.layoutChattingRoomItem.setOnClickListener {

                val intent = Intent(context, ChattingActivity::class.java)
                intent.putExtra("chattingRoomInfo", chattingRoom)
                startActivity(context, intent, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_chatting_room,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chattingRooms[position])
    }

    override fun getItemCount(): Int {
        return chattingRooms.size
    }
}