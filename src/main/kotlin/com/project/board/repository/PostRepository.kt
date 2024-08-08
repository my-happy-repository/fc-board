package com.project.board.repository

import com.project.board.domain.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post ,Long> {

}
