package com.kusitms29.backendH.domain.category.repository;

import com.kusitms29.backendH.domain.category.entity.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategory,Long> {
    List<UserCategory> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
