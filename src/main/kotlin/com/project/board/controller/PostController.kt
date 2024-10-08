package com.project.board.controller

import com.project.board.controller.dto.PostCreateRequest
import com.project.board.controller.dto.PostSearchRequest
import com.project.board.controller.dto.PostUpdateRequest
import com.project.board.controller.dto.toDto
import com.project.board.controller.dto.toPostSearchRequestDto
import com.project.board.controller.dto.toPostUpdateRequestDto
import com.project.board.controller.response.PostDetailResponse
import com.project.board.controller.response.PostSummaryResponse
import com.project.board.controller.response.toResponse
import com.project.board.controller.response.toSummaryResponse
import com.project.board.service.PostService
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

@RestController
class PostController(
    private val postService: PostService,
) {

    @PostMapping("/posts")
    fun createPost(
        @RequestBody postCreateRequest: PostCreateRequest,
    ): Long {
        return postService.createPost(requestDto = postCreateRequest.toDto())
    }

    @PutMapping("/posts/{id}")
    fun updatePost(
        @PathVariable(name = "id") id: Long,
        @RequestBody postUpdateRequest: PostUpdateRequest,
    ): Long {
        return postService.updatePost(id = id, requestDto = postUpdateRequest.toPostUpdateRequestDto())
    }

    @DeleteMapping("/posts/{id}")
    fun deletePost(
        @PathVariable(name = "id") id: Long,
        @RequestParam(name = "createdBy") createdBy: String,
    ): Long {
        return postService.deletePost(id = id, createdBy = createdBy)
    }

    @GetMapping("/posts/{id}")
    fun getPost(
        @PathVariable(name = "id") id: Long,
    ): PostDetailResponse {
        return postService.getPost(id = id).toResponse()
    }

    @GetMapping("/posts")
    fun getPosts(
        pageable: Pageable, // Pageable 인터페이스 사용 시 페이지 정보 자동으로 맵핑을 해 줌
        postSearchRequest: PostSearchRequest,
    ): Page<PostSummaryResponse> {
        return postService.findPageBy(
            pageRequest = pageable,
            postSearchRequestDto = postSearchRequest.toPostSearchRequestDto()
        ).toSummaryResponse()
    }
}
