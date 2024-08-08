package com.project.board.controller.dto

import org.springframework.web.bind.annotation.RequestParam

data class PostSearchRequest(
    @RequestParam(name = "title")
    val title: String,
    @RequestParam(name = "createdBy")
    val createdBy: String,
)
