package com.kusitms29.backendH.domain.category.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.kusitms29.backendH.domain.category.entity.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}