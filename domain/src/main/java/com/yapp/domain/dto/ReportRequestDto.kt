package com.yapp.domain.dto

data class ReportRequestDto(
    val boardId: Long,
    val reportType: Long,
    val content: String
)