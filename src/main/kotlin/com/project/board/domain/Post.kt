package com.project.board.domain

import com.project.board.config.AllOpen
import com.project.board.exception.PostNotUpdatableException
import com.project.board.service.dto.PostUpdateRequestDto
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@AllOpen
@Entity
open class Post(
    createdBy: String,
    title: String,
    content: String,
) : BaseEntity(
    createdBy = createdBy
) {

    // Id 전략 중 GenerationType.IDENTITY 는 DB 에서 값을 생성 해 줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var title: String = title
        protected set

    var content: String = content
        protected set

    fun update(postUpdateRequestDto: PostUpdateRequestDto) {
        // Update 하려는 작성자와 게시글 작성자가 동일하지 않을 시 예외가 발생 !
        if (postUpdateRequestDto.updatedBy != this.createdBy) {
            throw PostNotUpdatableException()
        }

        this.title = postUpdateRequestDto.title
        this.content = postUpdateRequestDto.content
        super.update(updatedBy = postUpdateRequestDto.updatedBy)
    }
}
