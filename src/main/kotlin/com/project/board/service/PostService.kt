package com.project.board.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true) // TODO - Transactional 어노테이션 왜 붙이는 지 확인이 필요 .... !!!
class PostService {

}
