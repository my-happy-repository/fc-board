package com.project.board.controller.dto

import com.project.board.service.dto.CommentResponseDto
import java.time.LocalDateTime
import javax.xml.stream.events.Comment

data class CommentResponse(
    val id: Long,
    val content: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun CommentResponseDto.toResponse() = CommentResponse(
    id = this.id,
    content = this.content,
    createdBy = this.createdBy,
    createdAt = this.createdAt,
)
