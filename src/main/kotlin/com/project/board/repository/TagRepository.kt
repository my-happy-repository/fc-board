package com.project.board.repository

import com.project.board.domain.Tag
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Long> {
    fun findByPostId(postId: Long): List<Tag>
}
