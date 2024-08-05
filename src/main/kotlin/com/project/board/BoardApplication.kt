package com.project.board

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BoardApplication

// TODO - JDBC 접근 오류 해결 필요 !
fun main(args: Array<String>) {
    runApplication<BoardApplication>(*args)
}
