package com.project.board.service.dto

import com.project.board.domain.Post
import java.time.LocalDateTime

data class PostDetailResponseDto(
    val id: Long,
    val title: String,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val comments: List<CommentResponseDto>,
    val tags: List<String> = emptyList(),
    val likeCount: Long = 0,
)

fun Post.toDetailResponseDto(
    likeCount: Long,
): PostDetailResponseDto {
    return PostDetailResponseDto(
        id = id,
        title = title,
        content = content,
        createdBy = createdBy,
        createdAt = createdAt,
        comments = comments.map { it.toResponseDto() },
        tags = tags.map { it.name },
        likeCount = likeCount
    )
}
