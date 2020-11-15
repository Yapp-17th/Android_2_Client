package com.example.sport_planet.data.model

data class ApplyListModel(
    val userId : Long,
    val userName : String,
val isHost : Boolean,
    val status : StatusModel
){
    data class StatusModel (
        val code : Int,
        val name: String
    )
}
