package com.kusitms29.backendH.api.community.service;

import com.kusitms29.backendH.domain.comment.entity.Comment;
import com.kusitms29.backendH.domain.comment.entity.CommentLike;
import com.kusitms29.backendH.domain.comment.service.CommentLikeModifier;
import com.kusitms29.backendH.domain.comment.service.CommentLikeReader;
import com.kusitms29.backendH.domain.comment.service.CommentReader;
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
public class CommentLikeService {
    private final UserReader userReader;
    private final CommentReader commentReader;
    private final CommentLikeReader commentLikeReader;
    private final CommentLikeModifier commentLikeModifier;

    public void createCommentLike(Long userId, Long commentId) {
        User user = userReader.findByUserId(userId);
        Comment comment = commentReader.findById(commentId);

        if(commentLikeReader.existsByCommentIdAndUserId(commentId, userId)) {
            throw new ConflictException(DUPLICATE_COMMENT_LIKE);
        }

        commentLikeModifier.save
                (CommentLike.builder()
                        .user(user)
                        .comment(comment)
                        .build());
    }

    public void deleteCommentLike(Long userId, Long commentId) {
        User user = userReader.findByUserId(userId);
        Comment comment = commentReader.findById(commentId);
        CommentLike commentLike = commentLikeReader.findByCommentIdAndUserId(commentId, userId);
        commentLikeModifier.delete(commentLike);
    }

}
