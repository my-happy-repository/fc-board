package com.project.board.service.dto

import com.project.board.domain.Comment
import java.time.LocalDateTime

data class CommentResponseDto(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun Comment.toResponseDto() = CommentResponseDto(
    id = id,
    content = content,
    createdBy = createdBy,
    createdAt = createdAt,
)
