package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.entity.CommentLike;
import com.kusitms29.backendH.domain.comment.repository.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentLikeModifier {
    private final CommentLikeRepository commentLikeRepository;

    public void save(CommentLike commentLike) {
        commentLikeRepository.save(commentLike);
    }

    public void delete(CommentLike commentLike) {
        commentLikeRepository.delete(commentLike);
    }

    public void deleteAllByCommentId(Long commentId) {
        commentLikeRepository.deleteAllByCommentId(commentId);
    }
}
