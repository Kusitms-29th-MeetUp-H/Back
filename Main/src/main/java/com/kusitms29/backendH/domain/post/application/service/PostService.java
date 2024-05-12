package com.kusitms29.backendH.domain.post.application.service;

import com.kusitms29.backendH.domain.comment.repository.CommentRepository;
import com.kusitms29.backendH.domain.post.application.controller.dto.response.PostResponseDto;
import com.kusitms29.backendH.domain.post.domain.Post;
import com.kusitms29.backendH.domain.post.domain.PostType;
import com.kusitms29.backendH.domain.post.repository.PostRepository;
import com.kusitms29.backendH.domain.postLike.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    public List<PostResponseDto> getPagingPostByPostType(Long userId, String postType, Pageable pageable) {
        PostType enumPostType = PostType.getEnumPostTypeFromStringPostType(postType);
        Page<Post> lifePosts = postRepository.findByPostType(enumPostType, pageable);
        return lifePosts.stream()
                .map(post -> mapToPostResponseDto(post, userId))
                .collect(Collectors.toList());
    }

    private PostResponseDto mapToPostResponseDto(Post post, Long userId) {
        int likeCount = postLikeRepository.countByPostId(post.getId());
        boolean isLikedByUser = postLikeRepository.existsByPostIdAndUserId(post.getId(), userId);
        int commentCount = commentRepository.countByPostId(post.getId());
        boolean isPostedByUser = post.getUser().getId() == userId;

        return PostResponseDto.of(
                post.getId(),
                post.getPostType(),
                post.getUser().getUserName(),
                post.getCreatedAt(),
                post.getTitle(),
                post.getContent(),
                likeCount,
                isLikedByUser,
                commentCount,
                isPostedByUser
        );
    }
}
