package com.project.board.service

import com.project.board.repository.CommentRepository
import com.project.board.service.dto.CommentCreateRequestDto
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
) : BehaviorSpec({
    given(name = "댓글 생성 시") {
        When(name = "인풋이 정상적으로 들어오면 ") {
            val commentContent = "댓글 내용"
            val commentCreatedBy = "댓글 작성자"

            val commentId = commentService.createComment(
                CommentCreateRequestDto(
                    postId = 1L,
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
            }
        }
    }
})
