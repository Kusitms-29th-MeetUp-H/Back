package com.kusitms29.backendH.domain.post.service;

import com.kusitms29.backendH.domain.post.entity.PostImage;
import com.kusitms29.backendH.domain.post.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostImageAppender {
    private final PostImageRepository postImageRepository;

    public PostImage save(PostImage postImage) {
        return postImageRepository.save(postImage);
    }

}
