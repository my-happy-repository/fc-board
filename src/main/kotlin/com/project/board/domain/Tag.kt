package com.project.board.domain

import jakarta.persistence.*

@Entity
class Tag(
    name: String,
    post: Post,
    createdBy: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0L

    open var name: String = name
        protected set // protected set 으로 외부 수정 불가 하도록 함

    @ManyToOne(fetch = FetchType.LAZY)
    open var post: Post = post
        protected set
}
