package com.project.board.controller.dto

import com.project.board.service.dto.CommentUpdateRequestDto

data class CommentUpdateRequest(
    val content: String,
    val updatedBy: String,
)

fun CommentUpdateRequest.toDto() = CommentUpdateRequestDto(
    content = this.content,
    updatedBy = this.updatedBy,
)
