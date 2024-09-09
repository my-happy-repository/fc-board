package com.project.board.controller.response

import com.project.board.service.dto.PostSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.LocalDateTime

data class PostSummaryResponse(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val tag: String? = null,
)

fun Page<PostSummaryResponseDto>.toSummaryResponse() =
    PageImpl(
        content.map { it.toResponse() },
        pageable,
        totalElements
    )

fun PostSummaryResponseDto.toResponse(): PostSummaryResponse =
    PostSummaryResponse(
        id = id,
        title = title,
        createdBy = createdBy,
        createdAt = createdAt
    )
