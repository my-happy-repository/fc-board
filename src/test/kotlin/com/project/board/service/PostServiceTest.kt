package com.project.board.service

import com.project.board.domain.Comment
import com.project.board.domain.Post
import com.project.board.exception.PostNotDeletableException
import com.project.board.exception.PostNotFoundException
import com.project.board.exception.PostNotUpdatableException
import com.project.board.repository.CommentRepository
import com.project.board.repository.PostRepository
import com.project.board.service.dto.PostCreateRequestDto
import com.project.board.service.dto.PostSearchRequestDto
import com.project.board.service.dto.PostUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class PostServiceTest(
    private val postService: PostService,
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
) : BehaviorSpec({
    beforeSpec {
        postRepository.saveAll(
            listOf(
                Post(title = "title1", content = "content1", createdBy = "createdBy-1"),
                Post(title = "title2", content = "content1", createdBy = "createdBy-1"),
                Post(title = "title3", content = "content1", createdBy = "createdBy-1"),
                Post(title = "title4", content = "content1", createdBy = "createdBy-1"),
                Post(title = "title5", content = "content1", createdBy = "createdBy-1"),
                Post(title = "title6", content = "content1", createdBy = "createdBy-1"),
                Post(title = "title7", content = "content1", createdBy = "createdBy-search"),
                Post(title = "title8", content = "content1", createdBy = "createdBy-1"),
                Post(title = "title-search", content = "content1", createdBy = "createdBy-1"),
                Post(title = "title10", content = "content1", createdBy = "createdBy-1")
            )
        )
    }

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

    given(name = "게시글 상세 조회 시") {
        val savedPost = postRepository.save(
            Post(
                title = "title",
                content = "content",
                createdBy = "harris"
            )
        )

        When(name = "정상 조회 시") {
            val post = postService.getPost(id = savedPost.id)
            then(name = "게시글의 내용이 정상적으로 반환됨을 확인한다.") {
                post.id shouldBe savedPost.id
                post.title shouldBe "title"
                post.content shouldBe "content"
                post.createdBy shouldBe "harris"
            }
        }

        When(name = "게시글이 없을 때") {
            then(name = "게시글을 찾을 수 없다는 에러가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    postService.getPost(id = 999L)
                }
            }
        }

        When(name = "댓글 추가 시") {
            commentRepository.save(Comment(content = "댓글 내용 1", post = savedPost, createdBy = "댓글 작성자 1"))
            commentRepository.save(Comment(content = "댓글 내용 2", post = savedPost, createdBy = "댓글 작성자 2"))
            commentRepository.save(Comment(content = "댓글 내용 3", post = savedPost, createdBy = "댓글 작성자 3"))

            then(name = "댓글이 정상 조회 됨을 확인한다") {
                val post = postService.getPost(savedPost.id)

                post.comments.size shouldBe 3
                post.comments[0].content shouldBe "댓글 내용 1"
                post.comments[1].content shouldBe "댓글 내용 2"
                post.comments[2].content shouldBe "댓글 내용 3"

                post.comments[0].createdBy shouldBe "댓글 작성자 1"
                post.comments[1].createdBy shouldBe "댓글 작성자 2"
                post.comments[2].createdBy shouldBe "댓글 작성자 3"
            }
        }
    }

    // TODO - 해당 아래 부터 테스트 실패함 확인이 필요 !!
    // 테스트 통과 하도록 수정 해 주기 !!!
    given(name = "게시글 목록 조회 시") {
        When(name = "정상 조회 시") {
            val postPage = postService.findPageBy(
                pageRequest = PageRequest.of(0, 5),
                postSearchRequestDto = PostSearchRequestDto(title = "", createdBy = "")
            )
            then(name = "게시글 페이지가 반환 된다.") {
                postPage.number shouldBe 0

                postPage.size shouldBe 5
                postPage.content.size shouldBe 5
                postPage.content[0].title shouldContain "title"
                postPage.content[0].createdBy shouldContain "createdBy"
            }
        }

        // TODO - 테스트가 진행이 안됨 확인이 필요 !
        // Post(title = "title-search", content = "content1", createdBy = "createdBy-1"),

        When(name = "타이틀로 검색") {
            val postPage = postService.findPageBy(
                pageRequest = PageRequest.of(0, 5),
                postSearchRequestDto = PostSearchRequestDto(title = "title-search", createdBy = "")
            )

            then(name = "타이틀에 해당하는 게시글이 반환") {

                postPage.number shouldBe 0
                postPage.size shouldBe 0
                postPage.content.size shouldBe 5
                postPage.content.first().title shouldContain "title"
                postPage.content.first().createdBy shouldContain "createdBy"
            }
        }

        When(name = "작성자로 검색") {
            val postPage = postService.findPageBy(
                pageRequest = PageRequest.of(0, 5),
                postSearchRequestDto = PostSearchRequestDto(title = "", createdBy = "createdBy-search")
            )

            then(name = "작성자에 해당하는 게시글이 반환") {
                postPage.number shouldBe 0
                postPage.size shouldBe 5
                postPage.content.size shouldBe 5
                postPage.content.first().title shouldContain "title"
                postPage.content.first().createdBy shouldBe "createdBy-search"
            }
        }
    }
})
