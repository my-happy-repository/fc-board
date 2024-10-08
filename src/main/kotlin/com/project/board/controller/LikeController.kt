package com.project.board.controller

import com.project.board.service.LikeService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LikeController(
    private val likeService: LikeService,
) {

    @PostMapping("/posts/{postId}/likes")
    fun createLikes(
        @PathVariable(name = "postId") postId: Long,
        @RequestParam createdBy: String,
    ) {
        likeService.createLike(postId = postId, createdBy = createdBy)
    }
}
