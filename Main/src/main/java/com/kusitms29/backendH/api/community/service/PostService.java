package com.kusitms29.backendH.api.community.service;

import com.kusitms29.backendH.api.community.service.dto.request.PostCalculateDto;
import com.kusitms29.backendH.api.community.service.dto.request.PostCreateRequestDto;
import com.kusitms29.backendH.api.community.service.dto.response.PostCreateResponseDto;
import com.kusitms29.backendH.api.community.service.dto.response.PostDetailResponseDto;
import com.kusitms29.backendH.api.community.service.dto.response.PostResponseDto;
import com.kusitms29.backendH.domain.comment.service.CommentManager;
import com.kusitms29.backendH.domain.comment.service.CommentReader;
import com.kusitms29.backendH.domain.comment.service.ReplyManager;
import com.kusitms29.backendH.domain.post.entity.Post;
import com.kusitms29.backendH.domain.post.entity.PostImage;
import com.kusitms29.backendH.domain.post.entity.PostType;
import com.kusitms29.backendH.domain.post.service.*;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.global.error.exception.NotAllowedException;
import com.kusitms29.backendH.infra.config.AwsS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms29.backendH.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PostService {
    private final AwsS3Service awsS3Service;
    private final PostReader postReader;
    private final PostLikeManager postLikeManager;
    private final CommentManager commentManager;
    private final CommentReader commentReader;
    private final ReplyManager replyManager;
    private final PostImageReader postImageReader;
    private final UserReader userReader;
    private final PostAppender postAppender;
    private final PostImageAppender postImageAppender;

    public List<PostResponseDto> getPostByPostType(Long userId, String postType) {
        PostType enumPostType = PostType.getEnumPostTypeFromStringPostType(postType);
        List<Post> posts = postReader.findByPostType(enumPostType);

        posts.sort(Comparator.comparing(Post :: getCreatedAt).reversed());

        return posts.stream()
                .map(post -> mapToPostResponseDto(post, userId))
                .collect(Collectors.toList());
    }

    private PostResponseDto mapToPostResponseDto(Post post, Long userId) {
        PostCalculateDto postCalculateDto = calculatePostDetail(post, userId);
        PostImage postImage = postImageReader.findByPostIdAndIsRepresentative(post.getId(), true);

        return PostResponseDto.of(
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
    }

    public PostDetailResponseDto getDetailPost(Long userId, Long postId) {
        Post post = postReader.findById(postId);
        PostCalculateDto postCalculateDto = calculatePostDetail(post, userId);

        List<String> imageUrls = postImageReader.findByPostId(post.getId())
                .stream()
                .map(PostImage::getImage_url)
                .collect(Collectors.toList());

        return PostDetailResponseDto.of(
                post.getPostType().getStringPostType(),
                post.getUser().getProfile(),
                post.getUser().getUserName(),
                post.getCreatedAt(),
                post.getTitle(),
                post.getContent(),
                postCalculateDto.getLikeCount(),
                postCalculateDto.isLikedByUser(),
                postCalculateDto.getCommentCount(),
                postCalculateDto.isPostedByUser(),
                imageUrls
        );
    }

    public PostCalculateDto calculatePostDetail(Post post, Long userId) {
        int likeCount = postLikeManager.countByPostId(post.getId());
        boolean isLikedByUser = postLikeManager.existsByPostIdAndUserId(post.getId(), userId);

        int totalCommentCount = commentManager.countByPostId(post.getId());
        int replyCount = commentReader.findByPostId(post.getId()).stream()
                .mapToInt(comment -> replyManager.countByCommentId(comment.getId()))
                .sum();
        totalCommentCount += replyCount;

        boolean isPostedByUser = post.getUser().getId() == userId;
        return new PostCalculateDto(likeCount, isLikedByUser, totalCommentCount, isPostedByUser);
    }

    public PostCreateResponseDto createPost(Long userId, List<MultipartFile> images, PostCreateRequestDto requestDto) {
        User writer = userReader.findByUserId(userId);
        PostType postType = PostType.getEnumPostTypeFromStringPostType(requestDto.getPostType());

        String title = requestDto.getTitle();
        String content = requestDto.getContent();
        if(title.length() > 30) {
            throw new NotAllowedException(TOO_LONG_TITLE_NOT_ALLOWED);
        }
        if(content.length() > 300) {
            throw new NotAllowedException(TOO_LONG_CONTENT_NOT_ALLOWED);
        }
        if(images != null && !images.isEmpty() && images.size() > 5) {
            throw new NotAllowedException(TOO_MANY_IMAGES_NOT_ALLOWED);
        }

        Post newPost = postAppender.save
                (Post.builder()
                        .user(writer)
                        .postType(postType)
                        .title(requestDto.getTitle())
                        .content(requestDto.getContent())
                        .build());

        List<String> imageUrls = null;
        if(images != null && !images.isEmpty()) {
            imageUrls = awsS3Service.uploadImages(images);
            for(int i=0; i<images.size(); i++) {
                postImageAppender.save(PostImage.builder()
                        .post(newPost)
                        .image_url(imageUrls.get(i))
                        .isRepresentative(i == 0)
                        .build());
            }
        }

        return PostCreateResponseDto.of(
                newPost.getPostType().getStringPostType(),
                newPost.getTitle(),
                newPost.getContent(),
                imageUrls
        );
    }
}

