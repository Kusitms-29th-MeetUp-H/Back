package com.kusitms29.backendH.api.community.service;

import com.kusitms29.backendH.api.community.service.dto.response.CommentCreateResponseDto;
import com.kusitms29.backendH.api.community.service.dto.response.CommentResponseDto;
import com.kusitms29.backendH.domain.comment.entity.Comment;
import com.kusitms29.backendH.domain.comment.service.CommentLikeManager;
import com.kusitms29.backendH.domain.comment.service.CommentModifier;
import com.kusitms29.backendH.domain.comment.service.CommentReader;
import com.kusitms29.backendH.domain.post.entity.Post;
import com.kusitms29.backendH.domain.post.service.PostReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
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
    private final CommentReader commentReader;
    private final CommentLikeManager commentLikeManager;
    private final PostReader postReader;
    private final UserReader userReader;
    private final CommentModifier commentModifier;

    public List<CommentResponseDto> getCommentsInPost(Long userId, Long postId, Pageable pageable) {
        Page<Comment> comments = commentReader.findByPostId(postId, pageable);
        return comments.stream()
                .map(comment -> mapToCommentResponseDto(comment, userId))
                .collect(Collectors.toList());
    }

    private CommentResponseDto mapToCommentResponseDto(Comment comment, Long userId) {
        int commentLikeCnt = commentLikeManager.countByCommentId(comment.getId());
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
        Post post = postReader.findById(postId);
        User writer = userReader.findByUserId(userId);

        if(content.length() > 30) {
            throw new NotAllowedException(TOO_LONG_COMMENT_NOT_ALLOWED);
        }

        Comment newComment = commentModifier.save
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
