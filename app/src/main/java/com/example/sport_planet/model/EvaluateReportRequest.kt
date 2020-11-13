package com.example.sport_planet.model

data class EvaluateReportRequest(
    val userId : Long,
    val reportType : Long,
    val content : String
)