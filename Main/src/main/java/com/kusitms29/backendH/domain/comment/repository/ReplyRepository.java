package com.kusitms29.backendH.domain.comment.repository;

import com.kusitms29.backendH.domain.comment.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByCommentId(Long commentId);

    int countByCommentId(Long commentId);

    @Modifying
    @Transactional
    @Query("UPDATE Reply r SET r.reported = r.reported + 1 WHERE r.id = :replyId")
    void increaseReportedCount(Long replyId);

    void deleteAllByCommentId(Long commentId);

}
