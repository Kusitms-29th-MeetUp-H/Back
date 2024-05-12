package com.kusitms29.backendH.domain.post.repository;

import com.kusitms29.backendH.domain.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}
