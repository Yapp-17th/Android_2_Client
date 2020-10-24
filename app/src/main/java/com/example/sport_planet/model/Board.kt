package com.example.sport_planet.model

import java.time.LocalDateTime

data class Board(
    val title: String,
    val user: User,
    val category: Category,
    val address: Address,
    val tag: Tag,
    val startsAt: LocalDateTime
)