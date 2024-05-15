package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentManager {
    private final CommentRepository commentRepository;
    public int countByPostId(Long postId) {
        return commentRepository.countByPostId(postId);
    }
}
