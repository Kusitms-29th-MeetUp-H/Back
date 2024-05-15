package com.kusitms29.backendH.application.community.service;

import com.kusitms29.backendH.domain.post.application.controller.dto.response.PostSearchResponseDto;
import com.kusitms29.backendH.domain.post.domain.Post;
import com.kusitms29.backendH.domain.post.repository.PostPagingRepository;
import com.kusitms29.backendH.domain.post.repository.PostRepository;
import com.kusitms29.backendH.domain.user.domain.User;
import com.kusitms29.backendH.domain.user.repository.UserRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms29.backendH.global.error.ErrorCode.USER_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostSearchService {
    private final PostRepository postRepository;
    private final PostPagingRepository postPagingRepository;
    private final UserRepository userRepository;
    public List<PostSearchResponseDto> searchPosts(Long userId, String keyword, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
        if(keyword == null || keyword.isEmpty()) {
            return new ArrayList<>();
        }
        Page<Post> posts = postPagingRepository.searchByTitleOrContent(keyword, pageable);
        return posts.stream()
                .map(post -> PostSearchResponseDto.of(
                        post.getId(), post.getPostType().getStringPostType(),
                        post.getUser().getProfile(), post.getUser().getUserName(),
                        post.getTitle(), post.getContent(), post.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

}
