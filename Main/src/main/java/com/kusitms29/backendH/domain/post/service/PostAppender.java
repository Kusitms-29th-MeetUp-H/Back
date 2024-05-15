package com.kusitms29.backendH.domain.post.service;

import com.kusitms29.backendH.domain.post.entity.Post;
import com.kusitms29.backendH.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostAppender {
    private final PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }


}
