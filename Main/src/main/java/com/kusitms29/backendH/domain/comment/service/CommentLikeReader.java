package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.entity.CommentLike;
import com.kusitms29.backendH.domain.comment.repository.CommentLikeRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.kusitms29.backendH.global.error.ErrorCode.COMMENT_LIKE_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentLikeReader {
    private final CommentLikeRepository commentLikeRepository;

    public boolean existsByCommentIdAndUserId(Long commentId, Long userId) {
        return commentLikeRepository.existsByCommentIdAndUserId(commentId, userId);
    }
    public CommentLike findByCommentIdAndUserId(Long commentId, Long userId) {
        return commentLikeRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_LIKE_NOT_FOUND));
    }

    public List<CommentLike> findByCommentId(Long commentId) {
        return commentLikeRepository.findByCommentId(commentId);
    }

}
