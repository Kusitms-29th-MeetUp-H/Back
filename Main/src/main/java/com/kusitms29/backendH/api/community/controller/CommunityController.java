package com.kusitms29.backendH.api.community.controller;

import com.kusitms29.backendH.api.community.service.*;
import com.kusitms29.backendH.api.community.service.dto.request.CommentCreateRequestDto;
import com.kusitms29.backendH.api.community.service.dto.request.PostCreateRequestDto;
import com.kusitms29.backendH.api.community.service.dto.response.*;
import com.kusitms29.backendH.api.user.service.UserService;
import com.kusitms29.backendH.global.common.SuccessResponse;
import com.kusitms29.backendH.infra.config.auth.UserId;
import com.kusitms29.backendH.infra.external.clova.papago.PapagoService;
import com.kusitms29.backendH.infra.external.clova.papago.detection.LanguageDetectionRequest;
import com.kusitms29.backendH.infra.external.clova.papago.detection.LanguageDetectionResponse;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationRequest;
import com.kusitms29.backendH.infra.external.clova.papago.translation.TextTranslationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/community")
@RestController
public class CommunityController {
    private final UserService userService;
    private final PostService postService;
    private final PostSearchService postSearchService;
    private final PostLikeService postLikeService;
    private final CommentService commentService;
    private final CommentLikeService commentLikeService;
    private final ReplyService replyService;
    private final ReplyLikeService replyLikeService;
    private final PapagoService papagoService;

    @GetMapping("/banner-image")
    public ResponseEntity<SuccessResponse<?>> getLoginUserImage(@UserId Long userId) {
        BannerImageResponseDto responseDto = userService.getLoginUserImage(userId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/post")
    public ResponseEntity<SuccessResponse<?>> getPostByPostType(@UserId Long userId, @RequestParam String postType) {
        List<PostResponseDto> responseDto = postService.getPostByPostType(userId, postType);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<SuccessResponse<?>> getDetailPost(@UserId Long userId, @PathVariable Long postId) {
        PostDetailResponseDto responseDto = postService.getDetailPost(userId, postId);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/post")
    public ResponseEntity<SuccessResponse<?>> createPost(@UserId Long userId,
                                                         @RequestPart(required = false) List<MultipartFile> images,
                                                         @RequestPart PostCreateRequestDto requestDto) {
        PostCreateResponseDto responseDto = postService.createPost(userId, images, requestDto);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/post/search")
    public ResponseEntity<SuccessResponse<?>> searchPost(@UserId Long userId, @RequestParam String keyword) {
        List<PostSearchResponseDto> responseDtos = postSearchService.searchPosts(userId, keyword);
        return SuccessResponse.ok(responseDtos);
    }
    @PostMapping("/post/like/{postId}")
    public ResponseEntity<SuccessResponse<?>> createPostLike(@UserId Long userId, @PathVariable Long postId) {
        postLikeService.createPostLike(userId, postId);
        return SuccessResponse.ok(true);
    }
    @DeleteMapping("/post/like/{postId}")
    public ResponseEntity<SuccessResponse<?>> deletePostLike(@UserId Long userId, @PathVariable Long postId) {
        postLikeService.deletePostLike(userId, postId);
        return SuccessResponse.ok(true);
    }

    @GetMapping("/comment/{postId}")
    public ResponseEntity<SuccessResponse<?>> getCommentsInPost(@UserId Long userId, @PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.getCommentsInPost(userId, postId);
        return SuccessResponse.ok(comments);
    }
    @PostMapping("/comment/{postId}")
    public ResponseEntity<SuccessResponse<?>> createComment(@UserId Long userId, @PathVariable Long postId,
                                                            @RequestBody CommentCreateRequestDto content) {
        CommentCreateResponseDto commentCreateResponseDto = commentService.createComment(userId, postId, content.getContent());
        return SuccessResponse.ok(commentCreateResponseDto);
    }
    @PostMapping("/comment/like/{commentId}")
    public ResponseEntity<SuccessResponse<?>> createCommentLike(@UserId Long userId, @PathVariable Long commentId) {
        commentLikeService.createCommentLike(userId, commentId);
        return SuccessResponse.ok(true);
    }

    @DeleteMapping("/comment/like/{commentId}")
    public ResponseEntity<SuccessResponse<?>> deleteCommentLike(@UserId Long userId, @PathVariable Long commentId) {
        commentLikeService.deleteCommentLike(userId, commentId);
        return SuccessResponse.ok(true);
    }

    @PostMapping("/comment/report/{commentId}")
    public ResponseEntity<SuccessResponse<?>> reportComment(@UserId Long userId, @PathVariable Long commentId) {
        int reportedCount = commentService.reportComment(userId, commentId);
        return SuccessResponse.ok(true);
    }

    @PostMapping("/reply/{commentId}")
    public ResponseEntity<SuccessResponse<?>> createReply(@UserId Long userId, @PathVariable Long commentId,
                                                          @RequestBody CommentCreateRequestDto requestDto) {
        ReplyCreateResponseDto responseDto = replyService.createReply(userId, commentId, requestDto.getContent());
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping("/reply/like/{replyId}")
    public ResponseEntity<SuccessResponse<?>> createReplyLike(@UserId Long userId, @PathVariable Long replyId) {
        replyLikeService.createReplyLike(userId, replyId);
        return SuccessResponse.ok(true);
    }

    @DeleteMapping("/reply/like/{replyId}")
    public ResponseEntity<SuccessResponse<?>> deleteReplyLike(@UserId Long userId, @PathVariable Long replyId) {
        replyLikeService.deleteReplyLike(userId, replyId);
        return SuccessResponse.ok(true);
    }

    @PostMapping("/reply/report/{replyId}")
    public ResponseEntity<SuccessResponse<?>> reportReply(@UserId Long userId, @PathVariable Long replyId) {
        int reportedCount = replyService.reportReply(userId, replyId);
        return SuccessResponse.ok(true);
    }

    @PostMapping("/translate")
    public ResponseEntity<SuccessResponse<?>> translateText(@RequestBody TextTranslationRequest requestDto) {
        TextTranslationResponse responseDto = papagoService.translateText(requestDto);
        return SuccessResponse.ok(responseDto.getMessage().getResult());
    }

    @PostMapping("/check-language")
    public ResponseEntity<SuccessResponse<?>> whatLanguageIsIt(@RequestBody LanguageDetectionRequest requestDto) {
        LanguageDetectionResponse responseDto = papagoService.checkLanguage(requestDto);
        return SuccessResponse.ok(responseDto);
    }

}
