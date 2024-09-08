package com.project.board.domain

import com.project.board.config.AllOpen
import com.project.board.exception.CommentNotUpdatableException
import com.project.board.service.dto.CommentUpdateRequestDto
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
@AllOpen
open class Comment(
    content: String,
    post: Post,
    createdBy: String,
) : BaseEntity(createdBy = createdBy) {

    fun update(updateRequestDto: CommentUpdateRequestDto) {
        if (updateRequestDto.updatedBy != this.createdBy) {
            throw CommentNotUpdatableException()
        }

        this.content = updateRequestDto.content
        super.updatedBy = updateRequestDto.updatedBy
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0L

    open var content: String = content
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    open var post: Post = post
        protected set
}
