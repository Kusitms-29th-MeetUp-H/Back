package com.kusitms29.backendH.domain.post.service;

import com.kusitms29.backendH.domain.post.entity.Post;
import com.kusitms29.backendH.domain.post.entity.PostType;
import com.kusitms29.backendH.domain.post.repository.PostRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kusitms29.backendH.global.error.ErrorCode.POST_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostReader {

    public final PostRepository postRepository;

    public List<Post> findByPostType(PostType enumPostType) {
        return postRepository.findByPostType(enumPostType);
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(()-> new EntityNotFoundException(POST_NOT_FOUND));
    }

    public List<Post> searchByTitleOrContent(String keyword) {
        return postRepository.searchByTitleOrContent(keyword);
    }

}
