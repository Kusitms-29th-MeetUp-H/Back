package com.kusitms29.backendH.domain.post.repository;

import com.kusitms29.backendH.domain.post.entity.Post;
import com.kusitms29.backendH.domain.post.entity.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByPostType(PostType postType);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Post> searchByTitleOrContent(@Param("keyword") String keyword);

}
