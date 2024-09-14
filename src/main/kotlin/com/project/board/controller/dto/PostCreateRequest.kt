package com.project.board.controller.dto

import com.project.board.service.dto.PostCreateRequestDto

data class PostCreateRequest(
    val title: String,
    val content: String,
    val createdBy: String,
    val tags: List<String> = emptyList(),
)

fun PostCreateRequest.toDto(): PostCreateRequestDto {
    return PostCreateRequestDto(
        title = this.title,
        content = this.content,
        createdBy = this.createdBy,
        tags = this.tags
    )
}
