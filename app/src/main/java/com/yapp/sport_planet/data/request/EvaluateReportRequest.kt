package com.yapp.sport_planet.data.request

data class EvaluateReportRequest(
    val userId : Long,
    val reportType : Long,
    val content : String? = null
)