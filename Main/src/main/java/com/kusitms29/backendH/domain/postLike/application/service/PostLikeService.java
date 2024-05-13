package com.kusitms29.backendH.domain.postLike.application.service;

import com.kusitms29.backendH.domain.comment.domain.Comment;
import com.kusitms29.backendH.domain.post.domain.Post;
import com.kusitms29.backendH.domain.post.repository.PostRepository;
import com.kusitms29.backendH.domain.postLike.domain.PostLike;
import com.kusitms29.backendH.domain.postLike.repository.PostLikeRepository;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.ConflictException;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms29.backendH.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostLikeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public void createPostLike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        if(postLikeRepository.existsByPostIdAndUserId(postId, userId)) {
            throw new ConflictException(DUPLICATE_POST_LIKE);
        }

        postLikeRepository.save(
                PostLike.builder()
                        .user(user)
                        .post(post)
                        .build()
        );
    }

    public void deletePostLike(Long userId, Long postId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException(POST_NOT_FOUND));

        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userId)
                .orElseThrow(() -> new EntityNotFoundException(POST_LIKE_NOT_FOUND));

        postLikeRepository.delete(postLike);
    }
}
