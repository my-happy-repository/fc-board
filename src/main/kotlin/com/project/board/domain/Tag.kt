package com.project.board.domain

import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
open class Tag(
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
    @JoinColumn(foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))  // 조인 테이블 제약 조건을 설정 !, 제약 조건을 생성 안함
    open var post: Post = post
        protected set
}
