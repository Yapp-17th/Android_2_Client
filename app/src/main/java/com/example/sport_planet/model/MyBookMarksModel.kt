package com.example.sport_planet.model

data class MyBookMarksModel(
    val boardId : Long,
    val hostId : Long,
    val hostName : String,
    val title : String,
    val groupStatus : List<GroupStatusModel>,
    val exercise: String,
    val city : String,
    val isBookMark : Boolean,
    val recruitNumber : Int,
    val recruitedNumber : Int,
    val time : String
){
    data class GroupStatusModel(
    val code : Int,
    val name : String
    )
}