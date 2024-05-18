package com.kusitms29.backendH.api.community.service;


import com.kusitms29.backendH.api.community.service.dto.request.PostCalculateDto;
import com.kusitms29.backendH.api.community.service.dto.response.PostSearchResponseDto;
import com.kusitms29.backendH.domain.post.entity.Post;
import com.kusitms29.backendH.domain.post.entity.PostImage;
import com.kusitms29.backendH.domain.post.service.PostImageReader;
import com.kusitms29.backendH.domain.post.service.PostReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostSearchService {
    private final UserReader userReader;
    private final PostReader postReader;
    private final PostImageReader postImageReader;
    private final PostService postService;

    public List<PostSearchResponseDto> searchPosts(Long userId, String keyword) {
        User user = userReader.findByUserId(userId);

        if(keyword == null || keyword.isEmpty()) {
            return new ArrayList<>();
        }

        List<Post> posts = postReader.searchByTitleOrContent(keyword);
        return posts.stream()
                .map(post -> {
                        PostCalculateDto postCalculateDto = postService.calculatePostDetail(post, user.getId());
                        PostImage postImage = postImageReader.findByPostIdAndIsRepresentative(post.getId(), true);

                        return PostSearchResponseDto.of(
                                post.getId(),
                                post.getPostType().getStringPostType(),
                                post.getUser().getProfile(),
                                post.getUser().getUserName(),
                                post.getCreatedAt(),
                                post.getTitle(),
                                post.getContent(),
                                (postImage != null)  ? postImage.getImage_url() : null,
                                postCalculateDto.getLikeCount(),
                                postCalculateDto.isLikedByUser(),
                                postCalculateDto.getCommentCount(),
                                postCalculateDto.isPostedByUser()
                        );
                })
                .collect(Collectors.toList());
    }

}
