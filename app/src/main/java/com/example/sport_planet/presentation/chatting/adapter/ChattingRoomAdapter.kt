package com.example.sport_planet.presentation.chatting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_planet.R
import com.example.sport_planet.data.model.ChatRoomInfo
import com.example.sport_planet.data.response.ChattingMessageResponse
import com.example.sport_planet.databinding.ItemChattingRoomBinding
import com.example.sport_planet.data.response.ChattingRoomListResponse
import com.example.sport_planet.presentation.chatting.UserInfo
import com.example.sport_planet.presentation.chatting.view.ChattingActivity
import com.example.sport_planet.util.Util
import com.perfomer.blitz.setTimeAgo
import kotlin.collections.ArrayList

class ChattingRoomAdapter(
    val context: Context,
    val chattingRoomItemLongClick: (ChattingRoomListResponse.Data) -> Unit
) : RecyclerView.Adapter<ChattingRoomAdapter.Holder>() {

    private var chattingRoomsHashMap = HashMap<Long, ChattingRoomListResponse.Data>()
    private var chattingRooms = ArrayList<ChattingRoomListResponse.Data>()

    fun settingChattingRoomList(chattingRoomList: HashMap<Long, ChattingRoomListResponse.Data>){
        chattingRoomsHashMap = chattingRoomList
        chattingRooms = ArrayList(chattingRoomsHashMap.values)
        chattingRooms.sortByDescending { chattingRoom -> chattingRoom.lastMessage.createdAt }
        notifyDataSetChanged()
    }

    fun updateChattingRoomList(chattingRoomId: Long, lastMessage: ChattingMessageResponse){
        chattingRoomsHashMap[chattingRoomId]!!.apply {
            this.lastMessage = lastMessage
            this.unreadMessages += 1
        }
        chattingRooms = ArrayList(chattingRoomsHashMap.values)
        chattingRooms.sortByDescending { chattingRoom -> chattingRoom.lastMessage.createdAt }
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemChattingRoomBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(chattingRoom: ChattingRoomListResponse.Data){

            binding.itemChattingRoom = chattingRoom

            binding.tvChattingRoomLastMessageTimestamp.setTimeAgo(Util.dateToMillis(chattingRoom.lastMessage.createdAt!!))

            binding.layoutChattingRoomItem.setOnClickListener {
                val intent = Intent(context, ChattingActivity::class.java)
                intent.putExtra("chatRoomInfo", ChatRoomInfo(chattingRoom.id, chattingRoom.boardId, chattingRoom.guestId, chattingRoom.hostId == UserInfo.USER_ID, chattingRoom.opponentNickname))
                startActivity(context, intent, null)
            }

            itemView.setOnLongClickListener {
                chattingRoomItemLongClick(chattingRoom)
                true
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

    override fun getItemId(position: Int): Long {
        return chattingRooms[position].id
    }

    override fun getItemCount(): Int {
        return chattingRooms.size
    }
}