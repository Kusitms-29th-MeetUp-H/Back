package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.entity.Comment;
import com.kusitms29.backendH.domain.comment.repository.CommentRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kusitms29.backendH.global.error.ErrorCode.COMMENT_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentReader {
    private final CommentRepository commentRepository;
    public List<Comment> findByPostId(Long postId)  {
        return commentRepository.findByPostId(postId);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(COMMENT_NOT_FOUND));
    }
}
