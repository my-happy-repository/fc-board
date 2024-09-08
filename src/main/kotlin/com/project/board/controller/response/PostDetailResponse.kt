package com.project.board.controller.response

import com.project.board.controller.dto.CommentResponse
import com.project.board.controller.dto.toResponse
import com.project.board.service.dto.PostDetailResponseDto
import java.time.LocalDateTime

data class PostDetailResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponse>,
)

fun PostDetailResponseDto.toResponse(): PostDetailResponse =
    PostDetailResponse(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
        comments = comments.map { it.toResponse() }
    )
