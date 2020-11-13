package com.example.sport_planet.data.response

data class EvaluateListResponse(
    val status : Int,
    val success : Boolean,
    val message: String,
    val data : List<EvaluateListModel>
){
    data class EvaluateListModel(
        val userId : Long,
        val userName : String,
        val isHost : Boolean,
        val isLike : Boolean,
        val isDisLike : Boolean
    )
}