package com.project.board.service.dto

import com.project.board.domain.Comment
import com.project.board.domain.Post

data class CommentCreateRequestDto(
    val postId: Long,
    val content: String,
    val createdBy: String,
) {
    companion object {
        fun toComment(commentRequestDto: CommentCreateRequestDto, post: Post): Comment {
            return Comment(
                post = post,
                content = commentRequestDto.content,
                createdBy = commentRequestDto.createdBy,
            )
        }
    }
}
