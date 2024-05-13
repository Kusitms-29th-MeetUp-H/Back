package com.kusitms29.backendH.domain.post.application.controller.dto;

import com.kusitms29.backendH.domain.post.application.controller.dto.request.PostCreateRequestDto;
import com.kusitms29.backendH.domain.post.application.controller.dto.response.PostCreateResponseDto;
import com.kusitms29.backendH.domain.post.application.controller.dto.response.PostDetailResponseDto;
import com.kusitms29.backendH.domain.post.application.controller.dto.response.PostResponseDto;
import com.kusitms29.backendH.domain.post.application.service.PostService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponse<?>> getDetailPost(@UserId Long userId, @PathVariable Long postId) {
        PostDetailResponseDto responseDto = postService.getDetailPost(userId, postId);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createPost(@UserId Long userId,
                                                         @RequestPart List<MultipartFile> images,
                                                         @RequestPart PostCreateRequestDto requestDto) {
        PostCreateResponseDto responseDto = postService.createPost(userId, images, requestDto);
        return SuccessResponse.ok(responseDto);
    }

}
