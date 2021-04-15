package com.yapp.domain.dto

data class EvaluateReportRequestDto (
    val userId : Long,
    val reportType : Long,
    val content : String? = null
        )