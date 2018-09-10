package com.popielarski.market.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PageableHelper {

    public static <T> Page<T> of(List<T> list, Pageable pageable) {
        return new PageImpl<>(list, pageable, list.size());
    }
}
