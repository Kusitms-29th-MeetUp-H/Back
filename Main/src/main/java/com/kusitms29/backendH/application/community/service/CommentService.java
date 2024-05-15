package com.kusitms29.backendH.application.community.service;

import com.kusitms29.backendH.domain.comment.application.controller.dto.response.CommentCreateResponseDto;
import com.kusitms29.backendH.domain.comment.application.controller.dto.response.CommentResponseDto;
import com.kusitms29.backendH.domain.comment.domain.Comment;
import com.kusitms29.backendH.domain.comment.repository.CommentPagingRepository;
import com.kusitms29.backendH.domain.comment.repository.CommentRepository;
import com.kusitms29.backendH.domain.commentLike.repository.CommentLikeRepository;
import com.kusitms29.backendH.domain.post.domain.Post;
import com.kusitms29.backendH.domain.post.repository.PostRepository;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import com.kusitms29.backendH.global.error.exception.NotAllowedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms29.backendH.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentPagingRepository commentPagingRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<CommentResponseDto> getCommentsInPost(Long userId, Long postId, Pageable pageable) {
        Page<Comment> comments = commentPagingRepository.findByPostId(postId, pageable);
        return comments.stream()
                .map(comment -> mapToCommentResponseDto(comment, userId))
                .collect(Collectors.toList());
    }

    private CommentResponseDto mapToCommentResponseDto(Comment comment, Long userId) {
        int commentLikeCnt = commentLikeRepository.countByCommentId(comment.getId());
        boolean isCommentedByUser = comment.getUser().getId() == userId;

        return CommentResponseDto.of(
                comment.getId(),
                comment.getUser().getProfile(),
                comment.getUser().getUserName(),
                comment.getCreatedAt(),
                comment.getContent(),
                commentLikeCnt,
                comment.getReported(),
                isCommentedByUser);
    }

    public CommentCreateResponseDto createComment(Long userId, Long postId, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));
        User writer = userRepository.findById(userId)
                .orElseThrow(()-> new EntityNotFoundException(USER_NOT_FOUND));

        if(content.length() > 30) {
            throw new NotAllowedException(TOO_LONG_COMMENT_NOT_ALLOWED);
        }

        Comment newComment = commentRepository.save
                (Comment.builder()
                        .post(post)
                        .user(writer)
                        .content(content)
                        .build());

        return CommentCreateResponseDto.of(
                newComment.getId(),
                newComment.getUser().getProfile(),
                newComment.getUser().getUserName(),
                newComment.getCreatedAt(),
                newComment.getContent(),
                newComment.getUser().getId() == userId
        );
    }


}
