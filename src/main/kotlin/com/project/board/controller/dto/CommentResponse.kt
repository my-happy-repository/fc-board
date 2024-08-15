package com.project.board.controller.dto

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)
