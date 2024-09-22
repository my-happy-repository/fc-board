package com.project.board.service

import com.project.board.domain.Post
import com.project.board.exception.CommentNotDeletableException
import com.project.board.exception.CommentNotFoundException
import com.project.board.exception.CommentNotUpdatableException
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.CommentRepository
import com.project.board.repository.PostRepository
import com.project.board.service.dto.CommentCreateRequestDto
import com.project.board.service.dto.CommentDeleteRequestDto
import com.project.board.service.dto.CommentUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.testcontainers.perSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.testcontainers.containers.GenericContainer

@SpringBootTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({
    val redisContainer = GenericContainer<Nothing>("redis:5.6")
    beforeSpec {
        redisContainer.portBindings.add("16379:6379")
        redisContainer.start()
        listener(redisContainer.perSpec()) // perSpec 이유는 Spec 단위 마다 실행, Spec 종료 시 레디스도 꺼짐
    }

    afterSpec {
        redisContainer.stop()
    }

    given(name = "댓글 생성 시") {
        val post = postRepository.save(
            Post(
                title = "게시글 작성자",
                content = "게시글 내용",
                createdBy = "게시글 작성자"
            )
        )

        When(name = "인풋이 정상적으로 들어오면 ") {
            val commentContent = "댓글 내용"
            val commentCreatedBy = "댓글 작성자"

            val commentId = commentService.createComment(
                postId = post.id,
                commentCreateRequestDto =
                CommentCreateRequestDto(
                    content = commentContent,
                    createdBy = commentCreatedBy
                )
            )

            then(name = "정상 생성 됨을 확인 한다.") {
                commentId shouldBeGreaterThan 0L
                val comment = commentRepository.findByIdOrNull(commentId) shouldNotBe null

                comment?.content shouldBe commentContent
                comment?.createdBy shouldBe commentCreatedBy
            }
        }

        When(name = "게시글이 존재하지 않으면") {
            then(name = "게시글 존재하지 않을 경우 예외가 발생한다.") {
                shouldThrow<PostNotFoundException> {
                    commentService.createComment(
                        postId = 999L,
                        commentCreateRequestDto = CommentCreateRequestDto(
                            content = "댓글 내용",
                            createdBy = "댓글 작성자"
                        )
                    )
                }
            }
        }
    }

    given(name = "댓글 수정 시") {
        val post = postRepository.save(
            Post(
                title = "게시글 작성자",
                content = "게시글 내용",
                createdBy = "게시글 작성자"
            )
        )

        val commentId = commentService.createComment(
            postId = post.id,
            commentCreateRequestDto =
            CommentCreateRequestDto(
                content = "댓글 내용",
                createdBy = "댓글 작성자"
            )
        )

        When(name = "사용자가 댓글을 정상적으로 수정 시") {
            val updateCommentId =
                commentService.updateComment(
                    commentId = commentId,
                    commentUpdateRequestDto = CommentUpdateRequestDto(
                        content = "내용 수정 테스트 입니다.",
                        updatedBy = "댓글 작성자"
                    )
                )
            then(name = "Update 시 반환 된 CommentId 값과 생성 한 CommentId 값이 동일 하여야 한다.") {
                commentId shouldBeEqual updateCommentId

                val findComment = commentRepository.findByIdOrNull(updateCommentId) shouldNotBe null
                findComment?.content shouldBe "내용 수정 테스트 입니다."
                findComment?.updatedBy shouldBe "댓글 작성자"
            }
        }

        When(name = "사용자가 존재하지 않는 댓글을 수정하려 할 시 ") {
            then(name = "CommentNotFoundException 이 발생하여야 합니다.") {
                shouldThrow<CommentNotFoundException> {
                    commentService.updateComment(
                        commentId = 999L,
                        commentUpdateRequestDto = CommentUpdateRequestDto(
                            content = "내용 수정 테스트 입니다.",
                            updatedBy = "댓글 작성자"
                        )
                    )
                }
            }
        }

        When(name = "업데이트 하려는 사용자와 작성자가 다르면") {
            then(name = "에러가 발생하여야 합니다.") {
                shouldThrow<CommentNotUpdatableException> {
                    commentService.updateComment(
                        commentId = commentId,
                        commentUpdateRequestDto = CommentUpdateRequestDto(
                            content = "댓글 내용 수정 테스트 입니다.",
                            updatedBy = "댓글 작성자가 아닙니다."
                        )
                    )
                }
            }
        }
    }

    given(name = "댓글 삭제 시") {
        val post = postRepository.save(
            Post(
                title = "게시글 작성자",
                content = "게시글 내용",
                createdBy = "게시글 작성자"
            )
        )
        val commentId = commentService.createComment(
            postId = post.id,
            commentCreateRequestDto =
            CommentCreateRequestDto(
                content = "댓글 내용",
                createdBy = "댓글 작성자"
            )
        )

        When(name = "댓글 생성자와 댓글 삭제자가 동일 하면") {
            then(name = "정상적으로 게시글을 삭제 합니다.") {
                val deletedCommentId = commentService.deleteComment(
                    commentId = commentId,
                    commentDeleteRequestDto = CommentDeleteRequestDto(
                        deletedBy = "댓글 작성자"
                    )
                )

                deletedCommentId shouldBe commentId
                commentRepository.findByIdOrNull(id = commentId) shouldBe null
            }
        }

        val newCommentId = commentService.createComment(
            postId = post.id,
            commentCreateRequestDto =
            CommentCreateRequestDto(
                content = "댓글 내용",
                createdBy = "댓글 작성자"
            )
        )

        When(name = "작성자와 삭제자가 다르면") {
            then(name = "삭제시 예외가 발생한다.") {
                shouldThrow<CommentNotDeletableException> {
                    commentService.deleteComment(
                        commentId = newCommentId,
                        commentDeleteRequestDto = CommentDeleteRequestDto(
                            deletedBy = "댓글 작성자가 아닙니다."
                        )
                    )
                }
            }
        }
    }
})
