package com.yapp.sport_planet.presentation.chatting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.perfomer.blitz.setTimeAgo
import com.yapp.sport_planet.R
import com.yapp.sport_planet.data.model.chatting.ChatRoomInfo
import com.yapp.sport_planet.data.response.chatting.ChattingMessageResponse
import com.yapp.sport_planet.databinding.ItemChattingRoomBinding
import com.yapp.sport_planet.data.response.chatting.ChattingRoomListResponse
import com.yapp.sport_planet.presentation.chatting.UserInfo
import com.yapp.sport_planet.presentation.chatting.view.ChattingActivity
import com.yapp.sport_planet.presentation.chatting.view.ChattingFragment
import com.yapp.sport_planet.util.Util
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
        chattingRoomsHashMap[chattingRoomId]!!.let {
            it.lastMessage = lastMessage
            if(ChattingFragment.currentChattingRoomNum != chattingRoomId)
                it.unreadMessages += 1
            else
                it.unreadMessages = 0
        }
        chattingRooms.sortByDescending { chattingRoom -> chattingRoom.lastMessage.createdAt }
        notifyDataSetChanged()
    }

    inner class Holder(private val binding: ItemChattingRoomBinding): RecyclerView.ViewHolder(binding.root){
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        fun bind(chattingRoom: ChattingRoomListResponse.Data){

            binding.itemChattingRoom = chattingRoom

            binding.tvChattingRoomLastMessageTimestamp.setTimeAgo(Util.dateToMillis(chattingRoom.lastMessage.createdAt))

            binding.layoutChattingRoomItem.setOnClickListener {

                chattingRoomsHashMap[chattingRoom.id]!!.unreadMessages = 0
                
                val intent = Intent(context, ChattingActivity::class.java)
                intent.putExtra("chatRoomInfo",
                    ChatRoomInfo(
                        chattingRoom.id,
                        chattingRoom.boardId,
                        chattingRoom.guestId,
                        chattingRoom.hostId == UserInfo.USER_ID,
                        chattingRoom.opponentNickname
                    )
                )
                ChattingFragment.currentChattingRoomNum = chattingRoom.id
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