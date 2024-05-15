package com.kusitms29.backendH.api.community.service;


import com.kusitms29.backendH.api.community.service.dto.response.PostSearchResponseDto;
import com.kusitms29.backendH.domain.post.entity.Post;
import com.kusitms29.backendH.domain.post.service.PostReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostSearchService {
    private final UserReader userReader;
    private final PostReader postReader;
    public List<PostSearchResponseDto> searchPosts(Long userId, String keyword, Pageable pageable) {
        User user = userReader.findByUserId(userId);

        if(keyword == null || keyword.isEmpty()) {
            return new ArrayList<>();
        }

        Page<Post> posts = postReader.searchByTitleOrContent(keyword, pageable);
        return posts.stream()
                .map(post -> PostSearchResponseDto.of(
                        post.getId(), post.getPostType().getStringPostType(),
                        post.getUser().getProfile(), post.getUser().getUserName(),
                        post.getTitle(), post.getContent(), post.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

}
