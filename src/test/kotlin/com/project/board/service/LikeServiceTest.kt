package com.project.board.service

import com.project.board.domain.Post
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.LikeRepository
import com.project.board.repository.PostRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class LikeServiceTest(
    private val likeService: LikeService,
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({
    given(name = "좋아요 생성 시") {
        val savedPost = postRepository.save(
            Post(
                createdBy = "post-created",
                title = "post-title",
                content = "post-content",
                tags = listOf("tag1", "tag2")
            )
        )

        When(name = "인풋이 정상 적으로 들어오면") {
            val likeId = likeService.createLike(savedPost.id, "created-test")
            then(name = "좋아요 ?! 가 정상적으로 생성 됨을 확인 한다 !") {
                val like = likeRepository.findByIdOrNull(id = likeId)

                like shouldNotBe null
                like?.createdBy shouldBe "created-test"
            }
        }

        When(name = "게시글이 존재하지 않으면") {
            then(name = "존재하지 않는 게시글 에러가 발생한다/") {
                shouldThrow<PostNotFoundException> {
                    likeService.createLike(9999L, "created-test")
                }
            }
        }
    }
})
