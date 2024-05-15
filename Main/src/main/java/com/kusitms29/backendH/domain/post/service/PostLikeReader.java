package com.kusitms29.backendH.domain.post.service;

import com.kusitms29.backendH.domain.post.entity.PostLike;
import com.kusitms29.backendH.domain.post.repository.PostLikeRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms29.backendH.global.error.ErrorCode.POST_LIKE_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostLikeReader {
    private final PostLikeRepository postLikeRepository;

    public PostLike findByPostIdAndUserId(Long postId, Long userId) {
        return postLikeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new EntityNotFoundException(POST_LIKE_NOT_FOUND));
    }
}
