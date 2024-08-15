package com.project.board.domain

import com.project.board.config.AllOpen
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

// 해당 어노테이션을 사용 시 BaseEntity 를 상속하면 내부에 값을 가질 수 있음
@MappedSuperclass
@AllOpen
abstract class BaseEntity(
    createdBy: String,
) {
    open val createdBy: String = createdBy

    open val createdAt: LocalDateTime = LocalDateTime.now()

    // protect set 을 해주는 이유는 외부 클래스에서 값을 변경하지 못하게 함
    open var updatedBy: String? = null
        protected set

    open var updatedAt: LocalDateTime? = null
        protected set

    fun update(updatedBy: String) {
        this.updatedBy = updatedBy
        this.updatedAt = LocalDateTime.now()
    }
}
