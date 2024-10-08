package com.project.board.controller.dto

import com.project.board.service.dto.PostUpdateRequestDto

data class PostUpdateRequest(
    val title: String,
    val content: String,
    val updatedBy: String,
    val tags: List<String> = emptyList(),
)

fun PostUpdateRequest.toPostUpdateRequestDto(): PostUpdateRequestDto {
    return PostUpdateRequestDto(
        title = this.title,
        content = this.content,
        updatedBy = this.updatedBy,
        tags = this.tags
    )
}
