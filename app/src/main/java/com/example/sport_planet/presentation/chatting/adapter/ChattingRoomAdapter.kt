package com.example.sport_planet.presentation.chatting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ItemChattingRoomBinding
import com.example.sport_planet.model.ChattingRoomResponse

class ChattingRoomAdapter : RecyclerView.Adapter<ChattingRoomAdapter.Holder>() {

    private var chattingRooms = ArrayList<ChattingRoomResponse>()

    fun addChattingRoom(room: ChattingRoomResponse){
        chattingRooms.add(room)
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemChattingRoomBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chattingRoom: ChattingRoomResponse){
            binding.tvChattingRoomLastMessageTimestamp.text = chattingRoom.lastMessage?.timestamp.toString()
            binding.tvChattingRoomLastMessageContent.text = chattingRoom.lastMessage?.content.toString()
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