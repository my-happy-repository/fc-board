package com.project.board.exception

open class PostException(message: String) : RuntimeException(message)

class PostNotFoundException() : PostException("게시글을 찾을 수 없습니다") // PostException 을 상속함

class PostNotUpdatableException() : PostException("게시글과 수정하려는 사람이 동일하지 않아 수정이 불가 합니다.")

class PostNotDeletableException() : PostException("작성자와 삭제하려는 사람이 달라 삭제가 불가 합니다.")
