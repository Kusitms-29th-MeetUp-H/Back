package com.kusitms29.backendH.global.error.exception;

import java.util.List;
import java.util.function.Supplier;

public class ListUtils {
    public static <T> List<T> throwIfEmpty(List<T> list, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (list.isEmpty()) {
            throw exceptionSupplier.get();
        }
        return list;
    }
    public <T> List<T> getListByTake(List<T> dtos, int take) {
        if (take == 0 || take >= dtos.size()) {
            return dtos;
        } else {
            return dtos.subList(0, take);
        }
    }
}
