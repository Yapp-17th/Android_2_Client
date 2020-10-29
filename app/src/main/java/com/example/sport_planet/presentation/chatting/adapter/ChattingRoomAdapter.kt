package com.example.sport_planet.presentation.chatting.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ItemChattingRoomBinding
import com.example.sport_planet.model.ChattingRoomResponse
import com.example.sport_planet.presentation.chatting.ChattingActivity

class ChattingRoomAdapter(val context: Context) : RecyclerView.Adapter<ChattingRoomAdapter.Holder>() {

    private var chattingRooms = ArrayList<ChattingRoomResponse>()

    fun addChattingRoom(room: ChattingRoomResponse){
        chattingRooms.add(room)
        chattingRooms.sortByDescending { chattingRoom -> chattingRoom.lastMessage.timestamp }
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemChattingRoomBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(chattingRoom: ChattingRoomResponse){
            binding.tvChattingRoomLastMessageTimestamp.text = chattingRoom.lastMessage?.timestamp.toString()
            binding.tvChattingRoomLastMessageContent.text = chattingRoom.lastMessage?.content.toString()
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