package com.project.board.exception

// TODO - kotlin sealed class 로 변경해보기 !
open class CommentException(message: String) : RuntimeException(message)

class CommentNotUpdatableException(message: String = "해당 사용자는 게시글 작성자가 아니므로 업데이트가 불가합니다.") : CommentException(message)

class CommentNotDeletableException(message: String = "댓글 작성자와 동일하여야 댓글 삭제가 가능 합니다.") : CommentException(message)
