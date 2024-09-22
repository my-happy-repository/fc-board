package com.project.board.event.listener

import com.project.board.domain.Like
import com.project.board.event.dto.LikeEvent
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.LikeRepository
import com.project.board.repository.PostRepository
import com.project.board.util.RedisUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.event.TransactionalEventListener

@Service
class LikenEventHandler(
    private val postRepository: PostRepository,
    private val likeRepository: LikeRepository,
    private val redisUtil: RedisUtil,
) {
    @TransactionalEventListener(LikeEvent::class)
    fun likeEventHandle(event: LikeEvent) {
        val post = postRepository.findByIdOrNull(id = event.postId)
            ?: throw PostNotFoundException()

        redisUtil.increment(redisUtil.getLikeCountKey(postId = event.postId))

        likeRepository.save(
            Like(
                post = post,
                createdBy = event.createdBy
            )
        )
    }
}
