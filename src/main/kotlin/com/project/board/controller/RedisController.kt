package com.project.board.controller

import com.project.board.util.RedisUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RedisController(
    private val redisUtil: RedisUtil,
) {
    @GetMapping("/redis")
    fun getRedisCount(): Long {
        redisUtil.increment(key = "count")
        return redisUtil.getCount(key = "count") ?: 0L
    }
}
