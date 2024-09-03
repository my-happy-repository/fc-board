package com.project.board.service.dto

data class CommentCreateRequestDto(
    val postId: Long,
    val content: String,
    val createdBy: String,
)
