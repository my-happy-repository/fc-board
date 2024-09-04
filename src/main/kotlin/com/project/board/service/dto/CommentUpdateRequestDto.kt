package com.project.board.service.dto

import com.project.board.controller.dto.CommentUpdateRequest
import com.project.board.domain.Comment

data class CommentUpdateRequestDto(
    val commentId: Long,
    val content: String,
)
