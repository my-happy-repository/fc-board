package com.project.board.service

import com.project.board.exception.CommentNotDeletableException
import com.project.board.exception.CommentNotFoundException
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.CommentRepository
import com.project.board.repository.PostRepository
import com.project.board.service.dto.CommentCreateRequestDto
import com.project.board.service.dto.CommentDeleteRequestDto
import com.project.board.service.dto.CommentUpdateRequestDto
import com.project.board.service.dto.toEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) {
    @Transactional
    fun createComment(
        postId: Long,
        commentCreateRequestDto: CommentCreateRequestDto,
    ): Long {
        val post = postRepository.findByIdOrNull(id = postId)
            ?: throw PostNotFoundException()

        return commentRepository.save(commentCreateRequestDto.toEntity(post)).id
    }

    @Transactional
    fun updateComment(
        commentId: Long,
        commentUpdateRequestDto: CommentUpdateRequestDto,
    ): Long {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw CommentNotFoundException("코멘트를 찾을 수 없습니다.")

        comment.update(updateRequestDto = commentUpdateRequestDto)

        return comment.id
    }

    @Transactional
    fun deleteComment(
        commentId: Long,
        commentDeleteRequestDto: CommentDeleteRequestDto,
    ): Long {
        val comment = commentRepository.findByIdOrNull(commentId)
            ?: throw CommentNotFoundException("Comment 조회가 불가능 합니다.")

        if (comment.createdBy != commentDeleteRequestDto.deletedBy) {
            throw CommentNotDeletableException()
        }

        commentRepository.deleteById(commentId)
        return comment.id
    }
}
