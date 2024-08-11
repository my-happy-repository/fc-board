package com.project.board.service

import com.project.board.domain.Post
import com.project.board.exception.PostNotDeletableException
import com.project.board.exception.PostNotFoundException
import com.project.board.exception.PostNotUpdatableException
import com.project.board.repository.PostRepository
import com.project.board.service.dto.PostCreateRequestDto
import com.project.board.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
) : BehaviorSpec({

    given(name = "게시글 생성 시") {
        When(name = "게시글 인풋이 정상적으로 들어오면") {
            val postId = postService.createPost(
                PostCreateRequestDto(
                    title = "title-test",
                    content = "content-test",
                    createdBy = "createdBy-test"
                )
            )

            then(name = "게시글이 정상적으로 생성 되었는 지 확인 한다.") {
                postId shouldBeGreaterThan 0
                val post = postRepository.findByIdOrNull(postId)
                post.shouldNotBeNull()

                post.title shouldBe "title-test"
                post.content shouldBe "content-test"
                post.createdBy shouldBe "createdBy-test"
            }
        }
    }

    given(name = "게시글 수정 시") {
        // 게시글 수정을 하기 위하여서는 게시글이 미리 생성 되어 있어여 한다.
        val saved = postRepository.save(
            Post(
                title = "title",
                content = "content",
                createdBy = "harris"
            )
        )

        When(name = "정상 수정 시") {
            val updatedId =
                postService.updatePost(
                    id = saved.id,
                    requestDto = PostUpdateRequestDto(
                        title = "update title",
                        content = "update content",
                        updatedBy = "harris"
                    )
                )

            then(name = "게시글이 정상적으로 수정 됨을 확인한다.") {
                saved.id shouldBe updatedId
                val updated = postRepository.findByIdOrNull(id = updatedId)

                updated.shouldNotBeNull()

                updated.title shouldBe "update title"
                updated.content shouldBe "update content"
                updated.updatedBy shouldBe "harris"
            }
        }

        When(name = "게시글이 없을 때") {
            then(name = "게시글이 찾을 수 없다는 오류가 발생") {
                shouldThrow<PostNotFoundException> {
                    postService.updatePost(
                        id = 999L,
                        requestDto = PostUpdateRequestDto(
                            title = "test-title",
                            content = "test-content",
                            updatedBy = "test-updatedBy"
                        )
                    )
                }
            }
        }

        When(name = "작성자가 동일하지 않으면") {
            then(name = "수정할 수 없는 게시물 입니다. 예외가 발생한다.") {
                shouldThrow<PostNotUpdatableException> {
                    postService.updatePost(
                        id = 1L,
                        requestDto = PostUpdateRequestDto(
                            title = "update title",
                            content = "update content",
                            updatedBy = "update harris"
                        )
                    )
                }
            }
        }
    }

    given(name = "게시글 삭제 시") {
        val savedPost = postRepository.save(
            Post(
                title = "title",
                content = "content",
                createdBy = "harris"
            )
        )

        When(name = "정상 삭제 시") {
            val deletePostId = postService.deletePost(id = savedPost.id, "harris")

            then(name = "게시글이 정상적으로 삭제 됨을 확인한다.") {
                deletePostId shouldBe savedPost.id
                postRepository.findByIdOrNull(id = deletePostId) shouldBe null
            }
        }

        When(name = "작성자가 동일하지 않으면") {
            // 이미 위에서 삭제가 되어 새로 만들어 줌
            val savedPost2 = postRepository.save(
                Post(
                    title = "title",
                    content = "content",
                    createdBy = "harris"
                )
            )

            then(name = "삭제 할 수 없는 게시물 입니다. 예외가 발생한다.") {
                shouldThrow<PostNotDeletableException> {
                    postService.deletePost(id = savedPost2.id, createdBy = "harris2")
                }
            }
        }
    }
})
