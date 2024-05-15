package com.kusitms29.backendH.domain.comment.service;

import com.kusitms29.backendH.domain.comment.entity.Reply;
import com.kusitms29.backendH.domain.comment.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ReplyReader {
    private final ReplyRepository replyRepository;

    public List<Reply> findByCommentId(Long commentId) {
        return replyRepository.findByCommentId(commentId);
    }
}
