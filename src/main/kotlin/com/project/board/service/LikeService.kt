package com.project.board.service

import com.project.board.event.dto.LikeEvent
import com.project.board.repository.LikeRepository
import com.project.board.repository.PostRepository
import com.project.board.util.RedisUtil
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val redisUtil: RedisUtil,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    fun createLike(postId: Long, createdBy: String) {
        applicationEventPublisher.publishEvent(LikeEvent(postId, createdBy))

// Redis 를 이용하지 않고 Event 를 사용하여 최적화를 시도
//        val post = postRepository.findByIdOrNull(postId)
//            ?: throw PostNotFoundException()
//
//        redisUtil.increment(redisUtil.getLikeCountKey(postId = postId))
//
//        likeRepository.save(
//            Like(
//                post = post,
//                createdBy = createdBy
//            )
//        ).id
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
