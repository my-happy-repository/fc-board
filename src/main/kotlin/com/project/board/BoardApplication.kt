package com.project.board

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync // 해당 어노테이션 추가하여야 비동기 작동이 가능
class BoardApplication

fun main(args: Array<String>) {
    runApplication<BoardApplication>(*args)
}
