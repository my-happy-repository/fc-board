package com.project.board.service

import com.project.board.domain.Like
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.LikeRepository
import com.project.board.repository.PostRepository
import com.project.board.util.RedisUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val redisUtil: RedisUtil,
) {
    @Transactional
    fun createLike(postId: Long, createdBy: String): Long {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw PostNotFoundException()

        redisUtil.increment(redisUtil.getLikeCountKey(postId = postId))

        return likeRepository.save(
            Like(
                post = post,
                createdBy = createdBy
            )
        ).id
    }

    fun countLike(postId: Long): Long {
        redisUtil.getCount(redisUtil.getLikeCountKey(postId = postId))?.let {
            return it
        }

        with(likeRepository.countByPostId(postId = postId)) {
            redisUtil.setData(redisUtil.getLikeCountKey(postId = postId), this)
            return this
        }
    }
}
