package com.project.board.service

import com.project.board.domain.Comment
import com.project.board.domain.Post
import com.project.board.domain.Tag
import com.project.board.exception.PostNotDeletableException
import com.project.board.exception.PostNotFoundException
import com.project.board.exception.PostNotUpdatableException
import com.project.board.repository.CommentRepository
import com.project.board.repository.PostRepository
import com.project.board.repository.TagRepository
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
    private val tagRepository: TagRepository,
    private val likeService: LikeService,
) : BehaviorSpec({
    beforeSpec {
        postRepository.saveAll(
            listOf(
                Post(title = "title1", content = "content1", createdBy = "createdBy-1", tags = listOf("tag1", "tag2")),
                Post(title = "title2", content = "content1", createdBy = "createdBy-1", tags = listOf("tag1", "tag2")),
                Post(title = "title3", content = "content1", createdBy = "createdBy-1", tags = listOf("tag1", "tag2")),
                Post(title = "title4", content = "content1", createdBy = "createdBy-1", tags = listOf("tag1", "tag2")),
                Post(title = "title5", content = "content1", createdBy = "createdBy-1", tags = listOf("tag1", "tag2")),
                Post(title = "title6", content = "content1", createdBy = "createdBy-1", tags = listOf("tag1", "tag2")),
                Post(
                    title = "title7",
                    content = "content1",
                    createdBy = "createdBy-se,arch",
                    tags = listOf("tag1", "tag2")
                ),
                Post(title = "title8", content = "content1", createdBy = "createdBy-1", tags = listOf("tag5")),
                Post(title = "title-search", content = "content1", createdBy = "create,dBy-1", tags = listOf("tag5")),
                Post(title = "title10", content = "content1", createdBy = "createdBy-1,", tags = listOf("tag5"))
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

        When(name = "태그가 추가되면") {
            val postId = postService.createPost(
                PostCreateRequestDto(
                    title = "title-test",
                    content = "content-test",
                    createdBy = "createdBy-test",
                    tags = listOf("tag1", "tag2")
                )
            )

            then(name = "태그가 정상적으로 추가됨을 확인한다.") {
                val tags = tagRepository.findByPostId(postId)

                tags.size shouldBe 2
                tags[0].name shouldBe "tag1"
                tags[1].name shouldBe "tag2"
            }
        }
    }

    given("게시글 수정 시") {
        // 게시글 수정을 하기 위하여서는 게시글이 미리 생성 되어 있어여 한다.
        val saved = postRepository.save(
            Post(
                title = "title",
                content = "content",
                createdBy = "harris",
                tags = listOf("tag1", "tag2")
            )
        )

        When("정상 수정 시") {
            val updatedId =
                postService.updatePost(
                    id = saved.id,
                    requestDto = PostUpdateRequestDto(
                        title = "update title",
                        content = "update content",
                        updatedBy = "harris"
                    )
                )

            then("게시글이 정상적으로 수정 됨을 확인한다.") {
                saved.id shouldBe updatedId
                val updated = postRepository.findByIdOrNull(id = updatedId)

                updated.shouldNotBeNull()

                updated.title shouldBe "update title"
                updated.content shouldBe "update content"
                updated.updatedBy shouldBe "harris"
            }
        }

        When("게시글이 없을 때") {
            then("게시글이 찾을 수 없다는 오류가 발생") {
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

        When("작성자가 동일하지 않으면") {
            then("수정할 수 없는 게시물 입니다. 예외가 발생한다.") {
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

        When("태그가 수정되었을 때") {
            val updatedId =
                postService.updatePost(
                    id = saved.id,
                    requestDto = PostUpdateRequestDto(
                        title = "update title",
                        content = "update content",
                        updatedBy = "harris",
                        tags = listOf("tag1", "tag2", "tag3")
                    )
                )

            then("정상적으로 수정 됨을 확인한다.") {
                val tags = tagRepository.findByPostId(postId = updatedId)

                tags.size shouldBe 3
                tags[2].name shouldBe "tag3"
            }

            then("태그 순서가 변경 되었을 때 정상적으로 변경 됨을 확인한다.") {
                postService.updatePost(
                    id = saved.id,
                    requestDto =
                    PostUpdateRequestDto(
                        title = "update title",
                        content = "update content",
                        updatedBy = "harris",
                        tags = listOf("tag3", "tag2", "tag1")
                    )
                )

                val tags = tagRepository.findByPostId(postId = updatedId)
                tags.size shouldBe 3
                tags[2].name shouldBe "tag1"
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

            then("삭제 할 수 없는 게시물 입니다. 예외가 발생한다.") {
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
                createdBy = "create-test"
            )
        )

        tagRepository.saveAll(
            listOf(
                Tag(name = "tag1", post = savedPost, createdBy = "create-test"),
                Tag(name = "tag2", post = savedPost, createdBy = "create-test"),
                Tag(name = "tag3", post = savedPost, createdBy = "create-test"),
                Tag(name = "tag4", post = savedPost, createdBy = "create-test"),
                Tag(name = "tag5", post = savedPost, createdBy = "create-test")
            )
        )

        likeService.createLike(postId = savedPost.id, createdBy = "like-test-1")
        likeService.createLike(postId = savedPost.id, createdBy = "like-test-2")
        likeService.createLike(postId = savedPost.id, createdBy = "like-test-3")

        When(name = "정상 조회 시") {
            val post = postService.getPost(id = savedPost.id)
            then(name = "게시글의 내용이 정상적으로 반환됨을 확인한다.") {
                post.id shouldBe savedPost.id

                post.title shouldBe "title"
                post.content shouldBe "content"
                post.createdBy shouldBe "create-test"
            }

            then(name = "태그가 정상적으로 조회가 된다") {
                post.tags.size shouldBe 5

                // TODO - 데이터 조회 순서 확인이 필요 !
                post.tags[0] shouldBe "tag1"
                post.tags[1] shouldBe "tag2"
                post.tags[2] shouldBe "tag3"
                post.tags[3] shouldBe "tag4"
                post.tags[4] shouldBe "tag5"
            }

            then(name = "좋아요 갯수가 조회 됨을 확인한다.") {
                post.likeCount shouldBe 3
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

            then(name = "첫번째 태그가 함께 조회 됨을 확인한다.") {
                postPage.content.forEach {
                    it.firstTag shouldBe "tag1"
                }
            }
        }

        When(name = "태그로 검색") {
            val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(tag = "tag5"))
            then(name = "태그에 해당하는 게시글이 반환이 된다.") {
                postPage.number shouldBe 0
                postPage.size shouldBe 3
                postPage.content.size shouldBe 3

                postPage.content[0].title shouldBe "title8"
                postPage.content[0].title shouldBe "title-search"
                postPage.content[0].title shouldBe "title10"
            }
        }

        When(name = "좋아요가 2개 추가 되었을 시 ") {
            val postPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(tag = "tag5"))

            postPage.forEach {
                likeService.createLike(
                    postId = it.id,
                    createdBy = "like-1"
                )

                likeService.createLike(
                    postId = it.id,
                    createdBy = "like-2"
                )
            }

            val likedPostPage = postService.findPageBy(PageRequest.of(0, 5), PostSearchRequestDto(tag = "tag5"))
            then(name = "좋아요 갯수가 정상적으로 조회 됨을 확인한다.") {
                likedPostPage.content.forEach {
                    it.likedCount shouldBe 2
                }
            }
        }
    }
})
