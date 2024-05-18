package com.kusitms29.backendH.api.community.service;

import com.kusitms29.backendH.api.community.service.dto.response.ReplyCreateResponseDto;
import com.kusitms29.backendH.domain.comment.entity.Comment;
import com.kusitms29.backendH.domain.comment.entity.Reply;
import com.kusitms29.backendH.domain.comment.service.CommentReader;
import com.kusitms29.backendH.domain.comment.service.ReplyLikeModifier;
import com.kusitms29.backendH.domain.comment.service.ReplyModifier;
import com.kusitms29.backendH.domain.comment.service.ReplyReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReplyService {
    private final UserReader userReader;
    private final CommentReader commentReader;
    private final ReplyReader replyReader;
    private final ReplyModifier replyModifier;
    private final ReplyLikeModifier replyLikeModifier;

    public ReplyCreateResponseDto createReply(Long userId, Long commentId, String content) {
        User user = userReader.findByUserId(userId);
        Comment comment = commentReader.findById(commentId);

        Reply newReply = replyModifier.save(
                Reply.builder()
                        .user(user)
                        .comment(comment)
                        .content(content)
                        .build()
        );

        return ReplyCreateResponseDto.of(newReply.getId(), newReply.getUser().getProfile(),
                newReply.getUser().getUserName(), newReply.getCreatedAt(),
                newReply.getContent(), newReply.getUser() == user);

    }

    public int reportReply(Long userId, Long replyId) {
        Reply reply = replyReader.findById(replyId);
        User user = userReader.findByUserId(userId);

        if(reply.getReported() >= 2) {
            replyLikeModifier.deleteAllByReplyId(replyId);
            replyModifier.delete(reply);
            return 3;
        }

        replyModifier.increaseReportedCount(replyId);
        return reply.getReported();
    }
}
