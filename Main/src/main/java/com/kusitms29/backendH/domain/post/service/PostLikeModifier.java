package com.kusitms29.backendH.domain.post.service;

import com.kusitms29.backendH.domain.post.entity.PostLike;
import com.kusitms29.backendH.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostLikeModifier {
    private final PostLikeRepository postLikeRepository;

    public void delete(PostLike postLike) {
        postLikeRepository.delete(postLike);
    }
}
