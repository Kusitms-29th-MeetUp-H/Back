package com.kusitms29.backendH.domain.commentLike.application.controller;

import com.kusitms29.backendH.domain.commentLike.application.service.CommentLikeService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/comment/like")
@RestController
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/{commentId}")
    public ResponseEntity<SuccessResponse<?>> createCommentLike(@UserId Long userId,
                                                                @PathVariable Long commentId) {
        commentLikeService.createCommentLike(userId, commentId);
        return SuccessResponse.ok(true);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<SuccessResponse<?>> deleteCommentLike(@UserId Long userId,
                                                                @PathVariable Long commentId) {
        commentLikeService.deleteCommentLike(userId, commentId);
        return SuccessResponse.ok(true);
    }

}
