package com.project.board.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @GetMapping("/test")
    fun test(): Array<String> {
        val listTest = listOf("1", "TEST", "2", "TEST-2")
        val test = arrayOf("Test-1", "Test-2")

        return test
    }
}
