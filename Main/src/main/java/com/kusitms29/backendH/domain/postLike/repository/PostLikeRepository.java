package com.kusitms29.backendH.domain.postLike.repository;

import com.kusitms29.backendH.domain.postLike.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    int countByPostId(Long postId);

    boolean existsByPostIdAndUserId(Long postId, Long userId);
}
