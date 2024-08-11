package com.project.board.repository

import com.project.board.domain.Post
import com.project.board.service.dto.PostSearchRequestDto
import com.querydsl.jpa.support.QPostgreSQLDialect
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository

interface CustomPostRepository {

    fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post>
}

class CustomPostRepositoryImpl: CustomPostRepository, QuerydslRepositorySupport(Post::class.java) {

    override fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post> {

        // TODO - Post 가 사용이 안되어 사용법 찾아보기 .... !!!
        // TODO - 확인이 필요 ... !!!
        from(Qpost.post)
            .where()

    }
}
