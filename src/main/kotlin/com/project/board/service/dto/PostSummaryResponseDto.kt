package com.project.board.service.dto

import com.project.board.domain.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import java.time.LocalDateTime

data class PostSummaryResponseDto(
    val id: Long,
    val title: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
)

fun Page<Post>.toSummaryResponseDto(): Page<PostSummaryResponseDto> {
    return PageImpl(
        content.map { it.toSummaryResponseDto() },
        pageable,
        totalElements
    )
}

fun Post.toSummaryResponseDto(): PostSummaryResponseDto {
    return PostSummaryResponseDto(
        id = id,
        title = title,
        createdBy = createdBy,
        createdAt = createdAt
    )
}
