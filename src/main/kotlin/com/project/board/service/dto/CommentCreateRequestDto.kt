package com.project.board.service.dto

import com.project.board.domain.Comment
import com.project.board.domain.Post

data class CommentCreateRequestDto(
    val postId: Long,
    val content: String,
    val createdBy: String,
)

fun CommentCreateRequestDto.toEntity(post: Post) = Comment(
    content = content,
    createdBy = createdBy,
    post = post
)
