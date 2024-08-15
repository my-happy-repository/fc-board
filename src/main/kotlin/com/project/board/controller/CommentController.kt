package com.project.board.controller

import com.project.board.controller.dto.CommentCreateRequest
import com.project.board.controller.dto.CommentUpdateRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CommentController {

    @PostMapping("/posts/{postId}/comments")
    fun createComments(
        @PathVariable(name = "postId") postId: Long,
        @RequestBody commentCreateRequest: CommentCreateRequest,
    ): Long {
        return 1L
    }

    @PutMapping("/comments/{commentId}")
    fun updateContent(
        @PathVariable(name = "commentId") commentId: Long,
        @RequestBody commentUpdateRequest: CommentUpdateRequest,
    ): Long {
        Thread.sleep(70000)

        return commentId
    }

    @DeleteMapping("/comments/{commentId}")
    fun deleteComment(
        @PathVariable(name = "commentId") commentId: Long,
        @RequestParam deletedBy: String,
    ): Long {
        return commentId
    }
}
