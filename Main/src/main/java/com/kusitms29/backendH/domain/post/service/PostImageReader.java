package com.kusitms29.backendH.domain.post.service;

import com.kusitms29.backendH.domain.post.entity.PostImage;
import com.kusitms29.backendH.domain.post.repository.PostImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostImageReader {
    private final PostImageRepository postImageRepository;

    public PostImage findByPostIdAndIsRepresentative(Long postId, boolean representative) {
        return postImageRepository.findByPostIdAndIsRepresentative(postId, representative);
    }

    public List<PostImage> findByPostId(Long postId) {
        return postImageRepository.findByPostId(postId);
    }

}
