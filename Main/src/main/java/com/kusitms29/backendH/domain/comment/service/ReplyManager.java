package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReplyManager {
    private final ReplyRepository replyRepository;

    public int countByCommentId(Long commentId) {
        return replyRepository.countByCommentId(commentId);
    }
}
