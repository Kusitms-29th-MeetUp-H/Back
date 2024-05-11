package com.kusitms29.backendH.domain.comment.repository;

import com.kusitms29.backendH.domain.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    int countByPostId(Long postId);
}
