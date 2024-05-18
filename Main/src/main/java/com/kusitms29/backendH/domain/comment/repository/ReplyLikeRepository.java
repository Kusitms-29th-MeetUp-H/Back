package com.kusitms29.backendH.domain.comment.repository;

import com.kusitms29.backendH.domain.comment.entity.ReplyLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    int countByReplyId(Long replyId);
    boolean existsByReplyIdAndUserId(Long replyId, Long userId);

    Optional<ReplyLike> findByReplyIdAndUserId(Long replyId, Long userId);

    List<ReplyLike> findByReplyId(Long replyId);

    void deleteAllByReplyId(Long replyId);
}
