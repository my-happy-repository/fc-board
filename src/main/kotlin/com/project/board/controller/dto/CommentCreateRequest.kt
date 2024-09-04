package com.project.board.controller.dto

import com.project.board.service.dto.CommentCreateRequestDto

data class CommentCreateRequest(
    val content: String,
    val createdBy: String,
) {
    companion object {
        fun toCommentCreateRequestDto(dto: CommentCreateRequest): CommentCreateRequestDto {
            return CommentCreateRequestDto(
                content = dto.content,
                createdBy = dto.createdBy
            )
        }
    }
}
