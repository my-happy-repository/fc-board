package com.project.board.repository

import com.project.board.domain.Post
import com.project.board.domain.QPost
import com.project.board.service.dto.PostSearchRequestDto
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
        val result =
            from(QPost.post)
                .where(
                    postSearchRequestDto.title?.let { QPost.post.title.contains(it) },
                    postSearchRequestDto.createdBy?.let { QPost.post.createdBy.eq(it) },
                    postSearchRequestDto.tag?.let { QPost.post.tags.any().name.eq(it) }
                )
                .fetchResults()

        return PageImpl(result!!.results, pageRequest, result.total)
    }
}
