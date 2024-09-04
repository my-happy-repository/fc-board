package com.project.board.service

import com.project.board.domain.Post
import com.project.board.exception.CommentNotFoundException
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.CommentRepository
import com.project.board.repository.PostRepository
import com.project.board.service.dto.CommentCreateRequestDto
import com.project.board.service.dto.CommentUpdateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull

@SpringBootTest
class CommentServiceTest(
    private val commentService: CommentService,
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
) : BehaviorSpec({
    given(name = "댓글 생성 시") {
        val post = postRepository.save(
            Post(
                title = "게시글 작성자",
                content = "게시글 내용",
                createdBy = "게시글 작성자",
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
                            createdBy = "댓글 작성자",
                        ),
                    )
                }
            }
        }
    }

    given(name = "사용자가 댓글을 수정하려 할시") {
        val post = postRepository.save(
            Post(
                title = "게시글 작성자",
                content = "게시글 내용",
                createdBy = "게시글 작성자",
            )
        )

        val commentId = commentService.createComment(
            postId = post.id,
            commentCreateRequestDto =
            CommentCreateRequestDto(
                content = "댓글 내용",
                createdBy = "댓글 작성자",
            )
        )

        When(name = "사용자가 댓글을 정상적으로 수정 시") {
            val updateCommentId =
                commentService.updateComment(
                    commentUpdateRequestDto = CommentUpdateRequestDto(
                        commentId = commentId,
                        content = "내용 수정 테스트 입니다."
                    )
                )
            then(name = "Update 시 반환 된 CommentId 값과 생성 한 CommentId 값이 동일 하여야 한다.") {
                commentId shouldBeEqual updateCommentId
            }
        }

        When(name = "사용자가 존재하지 않는 댓글을 수정하려 할 시 ") {
            commentService.updateComment(
                commentUpdateRequestDto = CommentUpdateRequestDto(
                    commentId = 999L,
                    content = "내용 수정 테스트 입니다."
                )
            )

            then(name = "CommentNotFoundException 이 발생하여야 합니다.") {
                shouldThrow<CommentNotFoundException> {
                    commentService.updateComment(
                        CommentUpdateRequestDto(
                            commentId = 999L,
                            content = "내용 수정 테스트 입니다."

                        )
                    )
                }
            }



        }
    }
})
