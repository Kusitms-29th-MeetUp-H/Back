package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.entity.Comment;
import com.kusitms29.backendH.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentModifier {
    private final CommentRepository commentRepository;

    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    public void increaseReportedCount(Long commentId) {
        commentRepository.increaseReportedCount(commentId);
    }

    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
