package com.project.board.service

import com.project.board.service.dto.CommentCreateRequestDto
import org.springframework.stereotype.Service

@Service
class CommentService {

    fun createComment(
        commentCreateRequestDto: CommentCreateRequestDto,
    ): Long {
        return 1L
    }
}
