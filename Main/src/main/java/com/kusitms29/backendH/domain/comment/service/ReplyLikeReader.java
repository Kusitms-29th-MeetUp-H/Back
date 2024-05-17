package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.entity.ReplyLike;
import com.kusitms29.backendH.domain.comment.repository.ReplyLikeRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms29.backendH.global.error.ErrorCode.REPLY_LIKE_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReplyLikeReader {
    private final ReplyLikeRepository replyLikeRepository;

    public ReplyLike findById(Long replyId) {
        return replyLikeRepository.findByReplyId(replyId)
                .orElseThrow(() -> new EntityNotFoundException(REPLY_LIKE_NOT_FOUND));
    }

    public boolean existsByReplyIdAndUserId(Long replyId, Long userId) {
        return replyLikeRepository.existsByReplyIdAndUserId(replyId, userId);
    }
}
