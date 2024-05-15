package com.kusitms29.backendH.infra.utils;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUtils {
    public <T> List<T> getListByTake(List<T> dtos, int take) {
        if (take == 0 || take >= dtos.size()) {
            return dtos;
        } else {
            return dtos.subList(0, take);
        }
    }
}
