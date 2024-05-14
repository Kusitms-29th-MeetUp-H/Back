package com.kusitms29.backendH.domain.comment.repository;

import com.kusitms29.backendH.domain.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentPagingRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findByPostId(Long postId, Pageable pageable);
}
