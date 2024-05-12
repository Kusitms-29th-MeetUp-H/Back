package com.kusitms29.backendH.domain.commentLike.repository;

import com.kusitms29.backendH.domain.commentLike.domain.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    int countByCommentId(Long commentId);
}
