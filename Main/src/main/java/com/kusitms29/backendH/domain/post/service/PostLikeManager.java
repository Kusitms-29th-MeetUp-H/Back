package com.kusitms29.backendH.domain.post.service;

import com.kusitms29.backendH.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostLikeManager {
    private final PostLikeRepository postLikeRepository;
    public int countByPostId(Long postId) {
        return postLikeRepository.countByPostId(postId);
    }

    public boolean existsByPostIdAndUserId(Long postId, Long userId) {
        return postLikeRepository.existsByPostIdAndUserId(postId, userId);
    }
}
