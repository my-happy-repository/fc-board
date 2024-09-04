package com.project.board.service

import com.project.board.domain.Comment
import com.project.board.domain.QComment.comment
import com.project.board.exception.CommentNotFoundException
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.CommentRepository
import com.project.board.repository.PostRepository
import com.project.board.service.dto.CommentCreateRequestDto
import com.project.board.service.dto.CommentUpdateRequestDto
import com.project.board.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) {
    fun createComment(
        postId: Long,
        commentCreateRequestDto: CommentCreateRequestDto,
    ): Long {
        val post = postRepository.findByIdOrNull(id = postId)
            ?: throw PostNotFoundException()

        return commentRepository.save(commentCreateRequestDto.toEntity(post)).id
    }

    fun updateComment (
        commentUpdateRequestDto: CommentUpdateRequestDto,
    ): Long {
        val comment = commentRepository.findByIdOrNull(id = commentUpdateRequestDto.commentId)
            ?: throw CommentNotFoundException("조회 하신 Comment 를 찾을 수 없습니다.")

        val post = postRepository.findByIdOrNull(id = comment.post.id)
            ?: throw PostNotFoundException()

        val updateComment =
            Comment(
                post = post,
                content = commentUpdateRequestDto.content,
                createdBy = comment.createdBy,
            )

        return commentRepository.save(updateComment).id
    }
}
