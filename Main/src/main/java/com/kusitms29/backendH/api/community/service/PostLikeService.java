package com.kusitms29.backendH.api.community.service;

import com.kusitms29.backendH.domain.post.entity.Post;
import com.kusitms29.backendH.domain.post.entity.PostLike;
import com.kusitms29.backendH.domain.post.service.*;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.global.error.exception.ConflictException;
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
    private final UserReader userReader;
    private final PostReader postReader;
    private final PostLikeManager postLikeManager;
    private final PostLikeAppender postLikeAppender;
    private final PostLikeReader postLikeReader;
    private final PostLikeModifier postLikeModifier;

    public void createPostLike(Long userId, Long postId) {
        User user = userReader.findByUserId(userId);
        Post post = postReader.findById(postId);

        if(postLikeManager.existsByPostIdAndUserId(postId, userId)) {
            throw new ConflictException(DUPLICATE_POST_LIKE);
        }

        postLikeAppender.save(
                PostLike.builder()
                        .user(user)
                        .post(post)
                        .build()
        );
    }

    public void deletePostLike(Long userId, Long postId) {
        User user = userReader.findByUserId(userId);
        Post post = postReader.findById(postId);
        PostLike postLike = postLikeReader.findByPostIdAndUserId(postId, userId);


        postLikeModifier.delete(postLike);
    }
}
