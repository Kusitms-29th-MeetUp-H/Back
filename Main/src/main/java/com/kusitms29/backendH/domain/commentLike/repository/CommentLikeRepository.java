package com.kusitms29.backendH.domain.commentLike.repository;

import com.kusitms29.backendH.domain.commentLike.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    int countByCommentId(Long commentId);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);

    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);
}
