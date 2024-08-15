package com.project.board.controller.dto

import com.project.board.service.dto.PostSearchRequestDto
import org.springframework.web.bind.annotation.RequestParam

data class PostSearchRequest(
    @RequestParam(name = "title")
    val title: String,
    @RequestParam(name = "createdBy")
    val createdBy: String,
)

fun PostSearchRequest.toPostSearchRequestDto(): PostSearchRequestDto =
    PostSearchRequestDto(
        title = title,
        createdBy = createdBy
    )
