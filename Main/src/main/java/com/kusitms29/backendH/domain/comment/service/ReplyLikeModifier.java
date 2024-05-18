package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.entity.Comment;
import com.kusitms29.backendH.domain.comment.entity.ReplyLike;
import com.kusitms29.backendH.domain.comment.repository.ReplyLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReplyLikeModifier {
    private final ReplyLikeRepository replyLikeRepository;
    public void save(ReplyLike replyLike) {
        replyLikeRepository.save(replyLike);
    }
    public void delete(ReplyLike replyLike) {
        replyLikeRepository.delete(replyLike);
    }

    public void deleteAllByReplyId(Long replyId) {
        replyLikeRepository.deleteAllByReplyId(replyId);
    }

}
