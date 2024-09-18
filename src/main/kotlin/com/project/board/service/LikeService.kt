package com.project.board.service

import com.project.board.domain.Like
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.LikeRepository
import com.project.board.repository.PostRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
) {
    @Transactional
    fun createLike(postId: Long, createdBy: String): Long {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw PostNotFoundException()

        return likeRepository.save(
            Like(
                post = post,
                createdBy = createdBy
            )
        ).id
    }

    fun countLike(postId: Long): Long {
        return likeRepository.countByPostId(postId = postId)
    }
}
