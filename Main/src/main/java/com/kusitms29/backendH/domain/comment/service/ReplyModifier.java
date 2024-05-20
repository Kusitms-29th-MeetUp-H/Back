package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.entity.Reply;
import com.kusitms29.backendH.domain.comment.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReplyModifier {
    private final ReplyRepository replyRepository;

    public Reply save(Reply reply) {
        return replyRepository.save(reply);
    }

    public void increaseReportedCount(Long replyId) {
        replyRepository.increaseReportedCount(replyId);
    }

    public void delete(Reply reply) {
        replyRepository.delete(reply);
    }

    public void deleteAllByCommentId(Long commentId) {
        replyRepository.deleteAllByCommentId(commentId);
    }
}
