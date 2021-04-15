package com.yapp.sport_planet.data.vo.mypage


data class MyViewVo(
        val category: List<String>,
        val city: String,
        val dislike: Int,
        val info: String,
        val isMine: Boolean,
        val like: Int,
        val userName: String
)