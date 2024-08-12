package com.project.board.repository

import com.project.board.domain.Post
import com.project.board.service.dto.PostSearchRequestDto
import com.querydsl.jpa.support.QPostgreSQLDialect
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.support.Querydsl
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.awt.desktop.QuitEvent

interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository

interface CustomPostRepository {

    fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post>
}

class CustomPostRepositoryImpl : CustomPostRepository, QuerydslRepositorySupport(Post::class.java) {
    override fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post>? {




//        val post = QPost.post
//        from()
//            .where()
        return null
    }
}
