package com.example.sport_planet.model.request

data class ReportRequest(
    val boardId: Long,
    val reportType: Long,
    val content: String
)