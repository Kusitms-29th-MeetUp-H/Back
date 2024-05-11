package com.kusitms29.backendH.domain.post.repository;

import com.kusitms29.backendH.domain.post.domain.Post;
import com.kusitms29.backendH.domain.post.domain.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long> {
    Page<Post> findByPostType(PostType postType, Pageable pageable);
}

