package com.kusitms29.backendH.domain.user.domain.service;

import com.kusitms29.backendH.domain.user.domain.UserCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryManager {
    public List<String> getTypeByUserCategories(List<UserCategory> userCategories){
        return userCategories.stream().map(userCategory -> String.valueOf(userCategory.getCategory().getType())).toList();
    }
}
