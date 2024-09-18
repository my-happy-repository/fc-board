package com.project.board.repository

import com.project.board.domain.QPost.post
import com.project.board.domain.QTag.tag
import com.project.board.domain.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
interface TagRepository : JpaRepository<Tag, Long>, CustomTagRepository {
    fun findByPostId(postId: Long): List<Tag>
}

interface CustomTagRepository {

    fun findPageBy(pageRequest: Pageable, tagName: String): Page<Tag>

}

class CustomTagRepositoryImpl : CustomTagRepository, QuerydslRepositorySupport(Tag::class.java) {
    override fun findPageBy(pageRequest: Pageable, tagName: String): Page<Tag> {
       return from(tag)
           // N+1 문제 해결을 위하여 join 을 직접 해 줌 !
           // Join 시 Post 를 같이 Join 해주었음 (뒤 post 지우면 join query 가 2번 나감 .. 원인은 확인해 보기 !)
           .join(tag.post(), post).fetchJoin()
           .where(tag.name.eq(tagName))
           .orderBy(tag.post().createdBy.desc())
           .offset(pageRequest.offset)
           .limit(pageRequest.pageSize.toLong())
           .fetchResults()
           .let { PageImpl(it.results, pageRequest, it.total) }
    }
}

