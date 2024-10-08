package com.project.board.domain

import com.project.board.config.AllOpen
import com.project.board.exception.PostNotUpdatableException
import com.project.board.service.dto.PostUpdateRequestDto
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@AllOpen
@Entity
open class Post(
    createdBy: String,
    title: String,
    content: String,
    tags: List<String> = emptyList(),
) : BaseEntity(
    createdBy = createdBy
) {
    // Id 전략 중 GenerationType.IDENTITY 는 DB 에서 값을 생성 해 줌
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0

    open var title: String = title
        protected set

    open var content: String = content
        protected set

    // TODO - OneToMany / ManyToOne 숙지 하기 !
    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = [CascadeType.ALL])
    open var comments: MutableList<Comment> = mutableListOf()
        protected set

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = [CascadeType.ALL])
    open var tags: MutableSet<Tag> = tags.map { Tag(it, this, createdBy) }.toMutableSet()
        protected set

    fun update(postUpdateRequestDto: PostUpdateRequestDto) {
        // Update 하려는 작성자와 게시글 작성자가 동일하지 않을 시 예외가 발생 !
        if (postUpdateRequestDto.updatedBy != this.createdBy) {
            throw PostNotUpdatableException()
        }

        this.title = postUpdateRequestDto.title
        this.content = postUpdateRequestDto.content
        replaceTags(tags = postUpdateRequestDto.tags)

        super.update(updatedBy = postUpdateRequestDto.updatedBy)
    }

    private fun replaceTags(tags: List<String>) {
        if (this.tags.map { it.name } != tags) {
            this.tags.clear()
            this.tags.addAll(tags.map { Tag(name = it, post = this, createdBy = this.createdBy) })
        }
    }
}
