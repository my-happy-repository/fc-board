package com.project.board.service

import com.project.board.repository.CommentRepository
import com.project.board.repository.PostRepository
import com.project.board.service.dto.CommentCreateRequestDto
import org.springframework.stereotype.Service

@Service
class CommentService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) {

    fun createComment(
        commentCreateRequestDto: CommentCreateRequestDto,
    ): Long {
        val post = postRepository.findById(commentCreateRequestDto.postId).get()
        val commentEntity = CommentCreateRequestDto.toComment(commentCreateRequestDto, post)

        val savedComment = commentRepository.save(
            commentEntity
        )

        return savedComment.id
    }
}
