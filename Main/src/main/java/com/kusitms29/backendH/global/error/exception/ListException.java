package com.kusitms29.backendH.global.error.exception;

import java.util.List;
import java.util.function.Supplier;

public class ListException {
    public static <T> List<T> throwIfEmpty(List<T> list, Supplier<? extends RuntimeException> exceptionSupplier) {
        if (list.isEmpty()) {
            throw exceptionSupplier.get();
        }
        return list;
    }
}
