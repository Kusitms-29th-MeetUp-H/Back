package com.kusitms29.backendH.domain.comment.application.service;

import com.kusitms29.backendH.domain.comment.application.controller.dto.response.CommentResponseDto;
import com.kusitms29.backendH.domain.comment.domain.Comment;
import com.kusitms29.backendH.domain.comment.repository.CommentRepository;
import com.kusitms29.backendH.domain.commentLike.repository.CommentLikeRepository;
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
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public List<CommentResponseDto> getCommentsInPost(Long userId, Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPostId(postId, pageable);
        return comments.stream()
                .map(comment -> mapToCommentResponseDto(comment, userId))
                .collect(Collectors.toList());
    }

    private CommentResponseDto mapToCommentResponseDto(Comment comment, Long userId) {
        int commentLikeCnt = commentLikeRepository.countByCommentId(comment.getId());
        boolean commentedByUser = comment.getUser().getId() == userId;

        return CommentResponseDto.of(
                comment.getId(),
                comment.getUser().getProfile(),
                comment.getUser().getUserName(),
                comment.getCreatedAt(),
                comment.getContent(),
                commentLikeCnt,
                comment.getReported(),
                commentedByUser);
    }


}
