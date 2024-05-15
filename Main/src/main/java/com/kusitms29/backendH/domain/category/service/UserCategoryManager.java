package com.kusitms29.backendH.domain.user.service;

import com.kusitms29.backendH.domain.category.domain.Type;
import com.kusitms29.backendH.domain.user.entity.UserCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryManager {
    public List<Type> getTypeByUserCategories(List<UserCategory> userCategories){
        return userCategories.stream().map(userCategory -> userCategory.getCategory().getType()).toList();
    }
}
