package com.project.board.controller.response

import com.project.board.service.dto.PostDetailResponseDto
import java.time.LocalDateTime

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponse>,
    val tags: List<String> = emptyList(),
    val likeCount: Long = 0,
)

fun PostDetailResponseDto.toResponse(): PostDetailResponse =
    PostDetailResponse(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
        comments = comments.map { it.toResponse() },
        tags = tags,
        likeCount = likeCount
    )
