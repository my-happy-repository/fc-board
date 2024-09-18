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
    val firstTag: String? = null,
    val likedCount: Long = 0,
)

fun Page<Post>.toSummaryResponseDto(countLike: (Long) -> Long): Page<PostSummaryResponseDto> {
    return PageImpl(
        content.map { it.toSummaryResponseDto(countLike) },
        pageable,
        totalElements
    )
}

fun Post.toSummaryResponseDto(countLike: (Long) -> Long): PostSummaryResponseDto {
    return PostSummaryResponseDto(
        id = id,
        title = title,
        createdBy = createdBy,
        createdAt = createdAt,
        firstTag = tags.firstOrNull()?.name,
        likedCount = countLike(id)
    )
}
