package com.example.sport_planet.presentation.chatting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.databinding.ItemChattingRoomBinding
import com.example.sport_planet.model.ChattingRoomResponse
import com.example.sport_planet.presentation.chatting.ChattingActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChattingRoomAdapter(val context: Context) : RecyclerView.Adapter<ChattingRoomAdapter.Holder>() {

    private var chattingRooms = ArrayList<ChattingRoomResponse>()
    val transformDate = SimpleDateFormat("a H:mm", Locale.KOREA)

    fun addChattingRoom(room: ChattingRoomResponse){
        chattingRooms.add(room)
        chattingRooms.sortByDescending { chattingRoom -> chattingRoom.lastMessage.timestamp }
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemChattingRoomBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SimpleDateFormat")
        fun bind(chattingRoom: ChattingRoomResponse){

            if (chattingRoom.lastMessage.isRead)
                binding.ivChattingRoomUnreadMessage.visibility = View.INVISIBLE

            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(chattingRoom.lastMessage.timestamp)
            binding.tvChattingRoomLastMessageTimestamp.text = transformDate.format(date)
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