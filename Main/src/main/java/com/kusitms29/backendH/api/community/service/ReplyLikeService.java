package com.kusitms29.backendH.api.community.service;

import com.kusitms29.backendH.domain.comment.entity.Reply;
import com.kusitms29.backendH.domain.comment.entity.ReplyLike;
import com.kusitms29.backendH.domain.comment.service.ReplyLikeModifier;
import com.kusitms29.backendH.domain.comment.service.ReplyLikeReader;
import com.kusitms29.backendH.domain.comment.service.ReplyReader;
import com.kusitms29.backendH.domain.user.entity.User;
import com.kusitms29.backendH.domain.user.service.UserReader;
import com.kusitms29.backendH.global.error.exception.ConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms29.backendH.global.error.ErrorCode.DUPLICATE_REPLY_LIKE;


@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReplyLikeService {
    private final UserReader userReader;
    private final ReplyReader replyReader;
    private final ReplyLikeReader replyLikeReader;
    private final ReplyLikeModifier replyLikeModifier;

    public void createReplyLike(Long userId, Long replyId) {
        User user = userReader.findByUserId(userId);
        Reply reply = replyReader.findById(replyId);
        if(replyLikeReader.existsByReplyIdAndUserId(replyId, userId)) {
            throw new ConflictException(DUPLICATE_REPLY_LIKE);
        }
        replyLikeModifier.save(
                ReplyLike.builder()
                        .user(user)
                        .reply(reply)
                        .build()
        );
    }

    public void deleteReplyLike(Long userId, Long replyId) {
        User user = userReader.findByUserId(userId);
        Reply reply = replyReader.findById(replyId);

        ReplyLike replyLike = replyLikeReader.findByReplyIdAndUserId(replyId, userId);
        replyLikeModifier.delete(replyLike);
    }
}
