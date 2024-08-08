package com.project.board.controller

import com.project.board.controller.dto.PostCreateRequest
import com.project.board.controller.dto.PostSearchRequest
import com.project.board.controller.dto.PostUpdateRequest
import com.project.board.controller.response.PostDetailResponse
import com.project.board.controller.response.PostSummaryResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PostController {

    @PostMapping("/posts")
    fun createPost(
        @RequestBody postCreateRequest: PostCreateRequest,
    ): Long {
        return 1L
    }

    @PutMapping("/posts/{id}")
    fun updatePost(
        @PathVariable(name = "id") id: Long,
        @RequestBody postUpdateRequest: PostUpdateRequest,
    ): Long {
        return 1L
    }

    @DeleteMapping("/posts/{id}")
    fun deletePost(
        @PathVariable(name = "id") id: Long,
        @RequestParam(name = "createdBy") createdBy: String,
    ): Long {
        return 1L
    }

    @GetMapping("/posts/{id}")
    fun getPost(
        @PathVariable(name = "id") id: Long,
    ): PostDetailResponse {
        return PostDetailResponse(
            id = 1L,
            title = "title",
            content = "content",
            createdBy = "createdBy",
            createdAt = LocalDateTime.now()
        )
    }

    @GetMapping("/posts")
    fun getPosts(
        pageable: Pageable, // Pageable 인터페이스 사용 시 페이지 정보 자동으로 맵핑을 해 줌
        postSearchRequest: PostSearchRequest,
    ): Page<PostSummaryResponse> {
        println("Title ${postSearchRequest.title}")
        println("CreatedBy ${postSearchRequest.createdBy}")

        return Page.empty()
    }
}
