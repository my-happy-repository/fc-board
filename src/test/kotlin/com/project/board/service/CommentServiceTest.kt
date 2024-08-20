package com.project.board.service

import com.project.board.domain.Post
import com.project.board.exception.PostNotFoundException
import com.project.board.repository.CommentRepository
import com.project.board.repository.PostRepository
import com.project.board.service.dto.CommentCreateRequestDto
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.comparables.shouldBeGreaterThan
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
        postRepository.save(
            Post(
                title = "게시글 제목",
                content = "게시글 내용",
                createdBy = "게시글 생성자"
            )
        )
        When(name = "인풋이 정상적으로 들어오면") {
            val commentId = commentService.createComment(
                postId = 1L,
                createRequestDto = CommentCreateRequestDto(
                    postId = 1L,
                    content = "댓글 내용",
                    createdBy = "댓글 생성자"
                )
            )

            Then(name = "정상 생성 됨을 확인한다.") {
                commentId shouldBeGreaterThan 0L
                val comment = commentRepository.findByIdOrNull(commentId)

                comment shouldNotBe null
                comment?.content shouldBe "댓글 내용"
                comment?.createdBy shouldBe "댓글 생성자"
            }
        }
        When(name = "게시글이 존재하지 않으면") {
            Then(name = "게시글 존재하지 않음 예외가 발생한다") {
                shouldThrow<PostNotFoundException> {
                    commentService.createComment(
                        postId = 999L,
                        createRequestDto = CommentCreateRequestDto(
                            postId = 1L,
                            content = "댓글 내용",
                            createdBy = "댓글 생성자"
                        )
                    )
                }
            }
        }
    }
})
