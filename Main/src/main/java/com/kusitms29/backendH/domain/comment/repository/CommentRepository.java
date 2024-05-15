package com.kusitms29.backendH.domain.comment.repository;

import com.kusitms29.backendH.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByPostId(Long postId);
    List<Comment> findByPostId(Long postId);

    @Modifying
    @Transactional
    @Query("UPDATE Comment c SET c.reported = c.reported + 1 WHERE c.id = :commentId")
    void increaseReportedCount(Long commentId);
}
