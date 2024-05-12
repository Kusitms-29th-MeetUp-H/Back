package com.kusitms29.backendH.domain.post.application.controller.dto;

import com.kusitms29.backendH.domain.post.application.controller.dto.response.PostResponseDto;
import com.kusitms29.backendH.domain.post.application.service.PostService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {

    private final PostService postService;
    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getPagingPostByPostType(@UserId Long userId, @RequestParam String postType, Pageable pageable) {
        List<PostResponseDto> responseDto = postService.getPagingPostByPostType(userId, postType, pageable);
        return SuccessResponse.ok(responseDto);
    }


}
