package com.kusitms29.backendH.domain.category.service;

import com.kusitms29.backendH.domain.category.entity.Category;
import com.kusitms29.backendH.domain.category.repository.CategoryRepository;
import com.kusitms29.backendH.global.error.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.kusitms29.backendH.global.error.ErrorCode.CATEGORY_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CategoryReader {
    private final CategoryRepository categoryRepository;

    public Category findByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException(CATEGORY_NOT_FOUND));
    }
}
