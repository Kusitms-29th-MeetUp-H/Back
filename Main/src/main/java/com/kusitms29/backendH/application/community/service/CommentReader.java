package com.kusitms29.backendH.application.community.service;

import com.kusitms29.backendH.domain.comment.domain.Comment;
import com.kusitms29.backendH.domain.comment.repository.CommentPagingRepository;
import com.kusitms29.backendH.domain.comment.repository.CommentRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms29.backendH.global.error.ErrorCode.COMMENT_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentReader {
    private final CommentPagingRepository commentPagingRepository;
    private final CommentRepository commentRepository;
    public Page<Comment> findByPostId(Long postId, Pageable pageable)  {
        return commentPagingRepository.findByPostId(postId, pageable);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));
    }
}
