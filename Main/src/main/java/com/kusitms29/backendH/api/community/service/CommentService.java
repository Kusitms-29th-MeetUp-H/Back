package com.kusitms29.backendH.api.community.service;

import com.kusitms29.backendH.api.community.service.dto.response.CommentCreateResponseDto;
import com.kusitms29.backendH.api.community.service.dto.response.CommentResponseDto;
import com.kusitms29.backendH.api.community.service.dto.response.ReplyResponseDto;
import com.kusitms29.backendH.domain.comment.entity.Comment;
import com.kusitms29.backendH.domain.comment.entity.Reply;
import com.kusitms29.backendH.domain.comment.service.*;
import com.kusitms29.backendH.domain.post.entity.Post;
import com.kusitms29.backendH.domain.post.service.PostReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.global.common.TimeCalculator;
import com.kusitms29.backendH.global.error.exception.NotAllowedException;
import com.kusitms29.backendH.infra.external.fcm.service.PushNotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.kusitms29.backendH.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CommentService {
    private final CommentReader commentReader;
    private final CommentLikeReader commentLikeReader;
    private final CommentLikeManager commentLikeManager;
    private final CommentLikeModifier commentLikeModifier;
    private final PostReader postReader;
    private final UserReader userReader;
    private final CommentModifier commentModifier;
    private final ReplyReader replyReader;
    private final ReplyModifier replyModifier;
    private final ReplyLikeReader replyLikeReader;
    private final ReplyLikeManager replyLikeManager;
    private final ReplyLikeModifier replyLikeModifier;
    private final PushNotificationService pushNotificationService;

    public List<CommentResponseDto> getCommentsInPost(Long userId, Long postId) {
        List<Comment> comments = commentReader.findByPostId(postId);
        return comments.stream()
                .map(comment -> mapToCommentResponseDto(comment, userId))
                .collect(Collectors.toList());
    }

    private CommentResponseDto mapToCommentResponseDto(Comment comment, Long userId) {
        User user = userReader.findByUserId(userId);
        int commentLikeCnt = commentLikeManager.countByCommentId(comment.getId());

        boolean isLikedByUser = commentLikeReader.findByCommentId(comment.getId())
                .stream()
                .anyMatch(commentLike -> commentLike.getUser().getId() == userId);

        boolean isCommentedByUser = comment.getUser().getId() == userId;

        List<Reply> replyList = replyReader.findByCommentId(comment.getId());
        List<ReplyResponseDto> replyResponseDto = replyList.stream()
                .map(reply ->
                        ReplyResponseDto.builder()
                        .replyId(reply.getId())
                        .writerImage(reply.getUser().getProfile())
                        .writerName(reply.getUser().getUserName())
                        .createdDate(TimeCalculator.calculateTimeDifference(reply.getCreatedAt()))
                        .content(reply.getContent())
                        .likeCnt(replyLikeManager.countByReplyId(reply.getId()))
                        .isLikedByUser(replyLikeReader.findByReplyId(reply.getId())
                                .stream().anyMatch(replyLike -> replyLike.getUser().getId() == userId))
                        .isRepliedByUser(reply.getUser().getId() == userId)
                        .reportedCnt(reply.getReported())
                        .build())
                .collect(Collectors.toList());

        return CommentResponseDto.of(
                comment.getId(),
                comment.getUser().getProfile(),
                comment.getUser().getUserName(),
                comment.getCreatedAt(),
                comment.getContent(),
                commentLikeCnt,
                isLikedByUser,
                comment.getReported(),
                isCommentedByUser,
                replyResponseDto
        );
    }

    public CommentCreateResponseDto createComment(Long userId, Long postId, String content) {
        Post post = postReader.findById(postId);
        User writer = userReader.findByUserId(userId);

        if(content.length() > 30) {
            throw new NotAllowedException(TOO_LONG_COMMENT_NOT_ALLOWED);
        }

        Comment newComment = commentModifier.save
                (Comment.builder()
                        .post(post)
                        .user(writer)
                        .content(content)
                        .build());

        pushNotificationService.sendCommentNotification(postId, newComment);

        return CommentCreateResponseDto.of(
                newComment.getId(),
                newComment.getUser().getProfile(),
                newComment.getUser().getUserName(),
                newComment.getCreatedAt(),
                newComment.getContent(),
                newComment.getUser().getId() == userId
        );
    }

    public int reportComment(Long userId, Long commentId) {
        Comment comment = commentReader.findById(commentId);
        User user = userReader.findByUserId(userId);

        if(comment.getReported() >= 2) {
            List<Reply> replyList = replyReader.findByCommentId(commentId);
            for(Reply reply : replyList) {
                //대댓글좋아요 삭제
                replyLikeModifier.deleteAllByReplyId(reply.getId());
            }
            //대댓글 삭제
            replyModifier.deleteAllByCommentId(commentId);
            //댓글 좋아요 삭제
            commentLikeModifier.deleteAllByCommentId(commentId);
            commentModifier.delete(comment);
            return 3;
        }

        commentModifier.increaseReportedCount(commentId);
        return comment.getReported();
    }


}
