package com.project.board.repository

import com.project.board.domain.Post
import com.project.board.domain.QPost
import com.project.board.domain.QPost.post
import com.project.board.service.dto.PostSearchRequestDto
import com.querydsl.core.QueryResults
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository

interface CustomPostRepository {

    fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post>
}

class CustomPostRepositoryImpl : CustomPostRepository, QuerydslRepositorySupport(Post::class.java) {

    override fun findPageBy(pageRequest: Pageable, postSearchRequestDto: PostSearchRequestDto): Page<Post> {
        // TODO - 에러가 발생 .... 확인이 필요 !
        val result =
                from(QPost.post)
                    .where(
                        postSearchRequestDto.title.let { post.title.contains(it) },
                        postSearchRequestDto.createdBy.let { post.createdBy.eq(it) }
                    )
                    .orderBy(post.createdAt.desc())
                    .offset(pageRequest.offset)
                    .limit(pageRequest.pageSize.toLong())
                    .fetchResults()


        return PageImpl(result!!.results, pageRequest, result.total)
    }
}
