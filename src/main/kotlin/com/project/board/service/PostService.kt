package com.project.board.service

import com.project.board.exception.PostNotDeletableException
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.PostRepository
import com.project.board.repository.TagRepository
import com.project.board.service.dto.PostCreateRequestDto
import com.project.board.service.dto.PostDetailResponseDto
import com.project.board.service.dto.PostSearchRequestDto
import com.project.board.service.dto.PostSummaryResponseDto
import com.project.board.service.dto.PostUpdateRequestDto
import com.project.board.service.dto.toDetailResponseDto
import com.project.board.service.dto.toEntity
import com.project.board.service.dto.toSummaryResponseDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true) // TODO - Transactional 어노테이션 왜 붙이는 지 확인이 필요 .... !!!
class PostService(
    private val postRepository: PostRepository,
    private val likeService: LikeService,
    private val tagRepository: TagRepository,
) {

    // Transactional Class 단위는 ReadOnly 함수단위는 ReadOnly 를 빼 줌, Create 할 때는 ReadOnly 를 뺴주어야 함 !
    // 더 구체적인 단위로 맞추어짐 (클래스 단위가 아닌 함수 단위 기준으로 맞추어짐)
    @Transactional
    fun createPost(requestDto: PostCreateRequestDto): Long {
        return postRepository.save(requestDto.toEntity()).id
    }

    @Transactional
    fun updatePost(
        id: Long,
        requestDto: PostUpdateRequestDto,
    ): Long {
        val post = postRepository.findByIdOrNull(id = id)
            ?: throw PostNotFoundException()

        // post.title = "TEST" 해당 식으로 수정할 수 없도록 protected set 으로 막아둠 !
        post.update(postUpdateRequestDto = requestDto)

        return id
    }

    @Transactional
    fun deletePost(id: Long, createdBy: String): Long {
        val post = postRepository.findByIdOrNull(id = id) ?: throw PostNotFoundException()

        if (post.createdBy != createdBy) {
            throw PostNotDeletableException()
        }
        postRepository.delete(post)
        return id
    }

    fun getPost(id: Long): PostDetailResponseDto {
        val likeCount = likeService.countLike(postId = id)

        return postRepository.findByIdOrNull(id)?.toDetailResponseDto(likeCount = likeCount)
            ?: throw PostNotFoundException()
    }

    fun findPageBy(
        pageRequest: Pageable,
        postSearchRequestDto: PostSearchRequestDto,
    ): Page<PostSummaryResponseDto> {
        postSearchRequestDto.tag?.let {
            return tagRepository.findPageBy(pageRequest, it).toSummaryResponseDto(likeService::countLike)
        }

        return postRepository.findPageBy(
            pageRequest = pageRequest,
            postSearchRequestDto = postSearchRequestDto
        ).toSummaryResponseDto(
            // 람다를 넘겨 줌 (좋아요 카운트는 LikeService 함수에 의존하게 됨)
            likeService::countLike
        )
    }
}
