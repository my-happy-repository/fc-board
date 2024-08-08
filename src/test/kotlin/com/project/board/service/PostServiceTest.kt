package com.project.board.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.beGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
): BehaviorSpec({
    given(name = "게시글 생성 시") {
        When(name = "게시글 정상 생성") {
            val postId = postService.createPost(PostUpateRequestDto(
                title = "title-test",
                content = "content-test",
                createdBy = "createdBy-test",
            ))

            then(name = "게시글이 정상적으로 생성 되었는 지 확인 한다.") {
                postId beGreaterThan(0L)
                val post = postRepository.findByIdOrNull(postId)
                shouldNotBeNull()



            }
        }
    }
})
