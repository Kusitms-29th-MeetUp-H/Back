package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.repository.ReplyLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReplyLikeManager {
    private final ReplyLikeRepository replyLikeRepository;
    public int countByReplyId(Long replyId) {
        return replyLikeRepository.countByReplyId(replyId);
    }
}
