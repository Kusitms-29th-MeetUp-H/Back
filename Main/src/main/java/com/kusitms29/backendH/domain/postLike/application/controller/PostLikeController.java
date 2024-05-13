package com.kusitms29.backendH.domain.postLike.application.controller;

import com.kusitms29.backendH.domain.postLike.application.service.PostLikeService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/post/like")
@RestController
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping("/{postId}")
    public ResponseEntity<SuccessResponse<?>> createCommentLike(/*@UserId Long userId,*/
                                                                @PathVariable Long postId) {
        postLikeService.createPostLike(Long.parseLong("2"), postId);
        return SuccessResponse.ok(true);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<SuccessResponse<?>> deleteCommentLike(/*@UserId Long userId,*/
                                                                @PathVariable Long postId) {
        postLikeService.deletePostLike(Long.parseLong("2"), postId);
        return SuccessResponse.ok(true);
    }

}
