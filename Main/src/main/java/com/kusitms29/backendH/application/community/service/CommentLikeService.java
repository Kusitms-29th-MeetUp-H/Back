package com.kusitms29.backendH.application.community.service;

import com.kusitms29.backendH.domain.comment.domain.Comment;
import com.kusitms29.backendH.domain.comment.repository.CommentRepository;
import com.kusitms29.backendH.domain.comment.domain.CommentLike;
import com.kusitms29.backendH.domain.comment.repository.CommentLikeRepository;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.ConflictException;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms29.backendH.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentLikeService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void createCommentLike(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));

        if(commentLikeRepository.existsByCommentIdAndUserId(commentId, userId)) {
            throw new ConflictException(DUPLICATE_COMMENT_LIKE);
        }

        commentLikeRepository.save
                (CommentLike.builder()
                        .user(user)
                        .comment(comment)
                        .build());
    }

    public void deleteCommentLike(Long userId, Long commentId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));

        CommentLike commentLike = commentLikeRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_LIKE_NOT_FOUND));

        commentLikeRepository.delete(commentLike);
    }



}
