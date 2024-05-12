package com.kusitms29.backendH.domain.comment.application.controller;

import com.kusitms29.backendH.domain.comment.application.controller.dto.response.CommentResponseDto;
import com.kusitms29.backendH.domain.comment.application.service.CommentService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponse<?>> getCommentsInPost(@UserId Long userId, @PathVariable Long postId, Pageable pageable) {
        List<CommentResponseDto> comments = commentService.getCommentsInPost(userId, postId, pageable);
        return SuccessResponse.ok(comments);
    }

}
