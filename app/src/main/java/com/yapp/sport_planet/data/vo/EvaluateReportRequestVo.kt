package com.yapp.sport_planet.data.vo

data class EvaluateReportRequestVo (
    val userId : Long,
    val reportType : Long,
    val content : String? = null
        )